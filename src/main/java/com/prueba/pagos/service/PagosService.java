package com.prueba.pagos.service;

import com.prueba.pagos.dto.PagoRequest;
import com.prueba.pagos.dto.PagoResponse;
import com.prueba.pagos.dto.StatusCambioEvent;
import com.prueba.pagos.exceptions.ServiceErrorException;
import com.prueba.pagos.mapper.PagoMapper;
import com.prueba.pagos.models.EstadosPagos;
import com.prueba.pagos.models.Pago;
import com.prueba.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PagosService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final RabbitProducer rabbitProducer;

    /**
     * @param pagoRequest
     * @return pagoResponse
     */
    public PagoResponse crearPago(PagoRequest pagoRequest) {
        Pago pago = pagoRepository.save(
                pagoMapper.toEntity(pagoRequest)
        );
        return pagoMapper.toResponse(pago);
    }

    /**
     * @param id
     * @return
     */
    public PagoResponse obtenerPagoPorId(Integer id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() ->
                new ServiceErrorException("El pago no existe", HttpStatus.NOT_FOUND));
        return pagoMapper.toResponse(pago);
    }

    /**
     * @param id
     * @param nuevoStatus
     * @return pagoResponse
     */
    public PagoResponse actualizarStatus(Integer id, String nuevoStatus) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() ->
                new ServiceErrorException("El pago no existe", HttpStatus.NOT_FOUND));

        String pagoStatusAnterior = pago.getEstadoPago().name();
        try {
            EstadosPagos estadoPago =
                    EstadosPagos.valueOf(nuevoStatus);

            pago.setEstadoPago(estadoPago);

        } catch (IllegalArgumentException e) {
            throw new ServiceErrorException("Status invalido", HttpStatus.BAD_REQUEST);
        }
        Pago pagoActualizado = pagoRepository.save(pago);

        rabbitProducer.publicaCambioStatus(
                new  StatusCambioEvent(id,pagoStatusAnterior,nuevoStatus)
        );
        return pagoMapper.toResponse(pagoActualizado);
    }

}

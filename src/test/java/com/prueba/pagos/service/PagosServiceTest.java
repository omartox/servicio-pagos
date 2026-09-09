package com.prueba.pagos.service;

import com.prueba.pagos.dto.PagoRequest;
import com.prueba.pagos.dto.PagoResponse;
import com.prueba.pagos.exceptions.ServiceErrorException;
import com.prueba.pagos.mapper.PagoMapper;
import com.prueba.pagos.models.EstadosPagos;
import com.prueba.pagos.models.Pago;
import com.prueba.pagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PagosServiceTest {
    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private RabbitProducer rabbitProducer;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagosService pagosService;

    @Test
    void crearPagoTest() {
        PagoRequest pagoRequest = new PagoRequest(
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.PENDIENTE
        );

        Pago pago = new Pago(
                1,
                pagoRequest.concepto(),
                pagoRequest.cantidad(),
                pagoRequest.pagador(),
                pagoRequest.receptor(),
                pagoRequest.monto(),
                pagoRequest.status()
        );

        when(pagoMapper.toEntity(any(PagoRequest.class))).thenReturn(pago);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
        when(pagoMapper.toResponse(any(Pago.class))).thenReturn(new PagoResponse(
                pago.getId(),
                pago.getConcepto(),
                pago.getCantidad(),
                pago.getPagador(),
                pago.getReceptor(),
                pago.getMonto(),
                pago.getEstadoPago()
        ));

        PagoResponse pagoResponse = pagosService.crearPago(pagoRequest);

        assertNotNull(pagoResponse);
        assertEquals(pago.getId(), pagoResponse.id());

        verify(pagoRepository).save(any(Pago.class));
        verify(pagoMapper).toEntity(any(PagoRequest.class));
        verify(pagoMapper).toResponse(any(Pago.class));

    }

    @Test
    void obtenerPagoPorIdTest() {
        Integer id = 1;
        Pago pago = new Pago(
                id,
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.PENDIENTE
        );

        when(pagoRepository.findById(id)).thenReturn(java.util.Optional.of(pago));
        when(pagoMapper.toResponse(any(Pago.class))).thenReturn(new PagoResponse(
                pago.getId(),
                pago.getConcepto(),
                pago.getCantidad(),
                pago.getPagador(),
                pago.getReceptor(),
                pago.getMonto(),
                pago.getEstadoPago()
        ));

        PagoResponse pagoResponse = pagosService.obtenerPagoPorId(id);

        assertNotNull(pagoResponse);
        assertEquals(pago.getId(), pagoResponse.id());

        verify(pagoRepository).findById(id);
        verify(pagoMapper).toResponse(any(Pago.class));

    }

    @Test
    void obtenerPagoPorIdNoExisteTest() {
        Integer id = 1;

        when(pagoRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ServiceErrorException.class, () -> pagosService.obtenerPagoPorId(id));

        verify(pagoRepository).findById(id);

    }

    @Test
    void actualizarStatus() {
        Integer id = 1;
        String nuevoStatus = "COMPLETADO";
        Pago pago = new Pago(
                id,
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.PENDIENTE
        );

        when(pagoRepository.findById(id)).thenReturn(java.util.Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
        when(pagoMapper.toResponse(any(Pago.class))).thenReturn(new PagoResponse(
                pago.getId(),
                pago.getConcepto(),
                pago.getCantidad(),
                pago.getPagador(),
                pago.getReceptor(),
                pago.getMonto(),
                EstadosPagos.COMPLETADO
        ));

        PagoResponse pagoResponse = pagosService.actualizarStatus(id, nuevoStatus);

        assertNotNull(pagoResponse);
        assertEquals(EstadosPagos.COMPLETADO, pagoResponse.estadoPago());

        verify(pagoRepository).findById(id);
        verify(pagoRepository).save(any(Pago.class));
        verify(pagoMapper).toResponse(any(Pago.class));
    }
}
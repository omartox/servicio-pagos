package com.prueba.pagos.mapper;

import com.prueba.pagos.dto.PagoRequest;
import com.prueba.pagos.dto.PagoResponse;
import com.prueba.pagos.models.Pago;
import org.springframework.stereotype.Component;


@Component
public class PagoMapper {
    public Pago toEntity(PagoRequest pagoRequest) {
        return new Pago(
                null,
                pagoRequest.concepto(),
                pagoRequest.cantidad(),
                pagoRequest.pagador(),
                pagoRequest.receptor(),
                pagoRequest.monto(),
                pagoRequest.status()
        );
    }

    public PagoResponse toResponse(Pago pago) {
        return new PagoResponse(
                pago.getId(),
                pago.getConcepto(),
                pago.getCantidad(),
                pago.getPagador(),
                pago.getReceptor(),
                pago.getMonto(),
                pago.getEstadoPago()
        );
    }

}

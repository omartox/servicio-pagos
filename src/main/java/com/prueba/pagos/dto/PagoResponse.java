package com.prueba.pagos.dto;

import com.prueba.pagos.models.EstadosPagos;

import java.math.BigDecimal;

public record PagoResponse(
    Integer id,
    String concepto,
    Integer cantidad,
    String pagador,
    String receptor,
    BigDecimal monto,
    EstadosPagos estadoPago
) {
}

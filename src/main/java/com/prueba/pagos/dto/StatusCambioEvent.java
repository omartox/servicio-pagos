package com.prueba.pagos.dto;

public record StatusCambioEvent(
        Integer idPago,
        String estatusAnterior,
        String estatusNuevo
) {
}

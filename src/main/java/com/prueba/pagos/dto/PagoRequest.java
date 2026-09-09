package com.prueba.pagos.dto;

import com.prueba.pagos.models.EstadosPagos;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagoRequest(
        @Schema(
                description = "Concepto del pago",
                example = "Mensualidad de servicio de internet"
        )
        @NotBlank
        String concepto,

        @Schema(
                description = "Cantidad de items del pago",
                example = "2"
        )
        @NotNull
        Integer cantidad,

        @Schema(
                description = "Indicador de quien hizo el pago",
                example = "Omar Toxqui"
        )
        @NotBlank
        String pagador,

        @Schema(
                description = "receptor del pago",
                example = "TotalPlay"
        )
        @NotBlank
        String receptor,

        @Schema(
                description = "Monto del pago",
                example = "500.00"
        )
        @NotNull
        BigDecimal monto,

        @Schema(
                description = "Estados en que se encuentra el pago",
                example = "PENDIENTE"
        )
        @NotNull
        EstadosPagos status
) {
}

package com.prueba.pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
        @Schema(
                description = "Mensaje de error",
                example = "No se encontro registro con el ID proporcionado"
        )
        String message
) {
}

package com.prueba.pagos.controller;

import com.prueba.pagos.dto.ErrorResponse;
import com.prueba.pagos.dto.PagoRequest;
import com.prueba.pagos.dto.PagoResponse;
import com.prueba.pagos.service.PagosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pagos")
public class PagoController {

    private final PagosService pagosService;


    @Operation(
            summary = "Registra un nuevo pago",
            description = "Registra un nuevo pago y devuelve el registro creado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago registrado correctamente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<PagoResponse> crearPago(@Valid @RequestBody PagoRequest pagoRequest) {
        return ResponseEntity.ok(pagosService.crearPago(pagoRequest));
    }

    @Operation(
            summary = "Consulta un pago por su ID",
            description = "Consulta el pago registrado en la base de datos por su ID y devuelve el registro encontrado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago encontrado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró un pago con el ID proporcionado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPagoPorId(
            @Parameter(
                    description = "Identificador del pago",
                    example = "1",
                    required = true
            )
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(pagosService.obtenerPagoPorId(id));
    }

    @Operation(
            summary = "Actualiza pago por ID",
            description = "Actualiza el estatus del pago por ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago Actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró un pago con el ID proporcionado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PagoResponse> actualizarStatusPago(
            @Parameter(
                    description = "Identificador del pago",
                    example = "1",
                    required = true
            )
            @PathVariable Integer id,
            @Parameter(
                    description = "Status del pago (COMPLETADO,\n" +
                            "PROGRESO,PENDIENTE,FALLIDO)",
                    example = "PENDIENTE",
                    required = true
            )
            @RequestParam String nuevoStatus) {
        return ResponseEntity.ok(pagosService.actualizarStatus(id, nuevoStatus));
    }
}

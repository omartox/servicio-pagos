package com.prueba.pagos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.pagos.dto.PagoRequest;
import com.prueba.pagos.dto.PagoResponse;
import com.prueba.pagos.models.EstadosPagos;
import com.prueba.pagos.service.PagosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagosService pagosService;


    @Test
    void crearPago() throws Exception {
        PagoRequest pagoRequest = new PagoRequest(
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.PENDIENTE
        );

        PagoResponse pagoResponse = new PagoResponse(
                1,
                pagoRequest.concepto(),
                pagoRequest.cantidad(),
                pagoRequest.pagador(),
                pagoRequest.receptor(),
                pagoRequest.monto(),
                pagoRequest.status()
        );

        when(pagosService.crearPago(any(PagoRequest.class))).thenReturn(pagoResponse);

        mockMvc.perform(post("/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pagoRequest)))
                .andExpect(status().isOk());

        verify(pagosService).crearPago(any(PagoRequest.class));
    }

    @Test
    void obtenerPagoPorIdTest() throws Exception {
        PagoResponse pagoResponse = new PagoResponse(
                1,
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.PENDIENTE
        );

        when(pagosService.obtenerPagoPorId(1)).thenReturn(pagoResponse);

        mockMvc.perform(
                get("/v1/pagos/{id}", 1)
        ).andExpect(status().isOk());

        verify(pagosService).obtenerPagoPorId(1);

    }

    @Test
    void actualizarStatusPago() throws Exception {

        when(pagosService.actualizarStatus(1, EstadosPagos.COMPLETADO.name())).thenReturn(new PagoResponse(
                1,
                "Pago de prueba",
                1,
                "omar toxqui",
                "empresa",
                new BigDecimal("300.00"),
                EstadosPagos.COMPLETADO
        ));
        mockMvc.perform(
                patch("/v1/pagos/{id}", 1)
                        .param("nuevoStatus", EstadosPagos.COMPLETADO.name())
        ).andExpect(status().isOk());

    }
}
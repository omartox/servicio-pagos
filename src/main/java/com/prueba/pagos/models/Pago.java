package com.prueba.pagos.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pagos")
public  class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String concepto;
    private Integer cantidad;
    private String pagador;
    private String receptor;
    private BigDecimal monto;
    @Enumerated(EnumType.STRING)
    private EstadosPagos estadoPago;
}

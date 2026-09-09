package com.prueba.pagos.repository;

import com.prueba.pagos.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago,Integer> {
}

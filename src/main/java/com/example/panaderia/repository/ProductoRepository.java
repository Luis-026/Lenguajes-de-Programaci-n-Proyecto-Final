package com.example.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.panaderia.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT SUM(p.stock) FROM Producto p WHERE p.activo = true")
    Integer obtenerStockTotal();

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.stock < 5 AND p.activo = true")
    Integer contarBajoStock();

}
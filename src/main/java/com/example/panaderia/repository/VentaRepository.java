package com.example.panaderia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.panaderia.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("select coalesce(sum(v.total), 0) from Venta v")
    double totalMontoVentas();

    @Query("select count(v) from Venta v")
    long totalComprobantes();

    @Query("select coalesce(sum(v.cantidad), 0) from Venta v")
    long totalProductosVendidos();

    @Query("""
           select v.producto.nombre, sum(v.cantidad) as totalCant
           from Venta v
           group by v.producto.nombre
           order by totalCant desc
           """)
    List<Object[]> productosMasVendidos();

    List<Venta> findTop10ByOrderByFechaDesc();
}
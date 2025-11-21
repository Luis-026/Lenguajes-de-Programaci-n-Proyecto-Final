package com.example.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.panaderia.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
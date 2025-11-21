package com.example.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.panaderia.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
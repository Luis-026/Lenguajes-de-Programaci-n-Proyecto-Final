package com.example.panaderia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.panaderia.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByActivoTrue();
}
package com.example.panaderia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.panaderia.entity.Producto;
import com.example.panaderia.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public void activar(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        producto.ifPresent(p -> {
            p.setActivo(true);
            productoRepository.save(p);
        });
    }

    public void anular(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        producto.ifPresent(p -> {
            p.setActivo(false);
            productoRepository.save(p);
        });
    }

    // ⭐ Nuevo: Total general de stock
    public int totalStock() {
        return productoRepository.findAll()
                .stream()
                .mapToInt(Producto::getStock)
                .sum();
    }

    // ⭐ Nuevo: Productos con bajo stock (< 10)
    public long productosBajoStock() {
        return productoRepository.findAll()
                .stream()
                .filter(p -> p.getStock() < 10)
                .count();
    }
}
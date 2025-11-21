package com.example.panaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.panaderia.entity.Categoria;
import com.example.panaderia.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public void activar(Long id) {
        categoriaRepository.findById(id).ifPresent(c -> {
            c.setActivo(true);
            categoriaRepository.save(c);
        });
    }

    public void anular(Long id) {
        categoriaRepository.findById(id).ifPresent(c -> {
            c.setActivo(false);
            categoriaRepository.save(c);
        });
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }
}
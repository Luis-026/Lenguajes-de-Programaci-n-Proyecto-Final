package com.example.panaderia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.panaderia.entity.Rol;
import com.example.panaderia.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    public void guardar(Rol rol) {
        rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    public void activar(Long id) {
        Optional<Rol> optional = rolRepository.findById(id);
        optional.ifPresent(r -> {
            r.setActivo(true);
            rolRepository.save(r);
        });
    }

    public void anular(Long id) {
        Optional<Rol> optional = rolRepository.findById(id);
        optional.ifPresent(r -> {
            r.setActivo(false);
            rolRepository.save(r);
        });
    }

    public Rol buscarPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }
}
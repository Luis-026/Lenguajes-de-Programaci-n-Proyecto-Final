package com.example.panaderia.service;

import com.example.panaderia.entity.Usuario;
import com.example.panaderia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario validarLogin(String usuario, String clave) {
        Optional<Usuario> opt = usuarioRepository.findByUsuarioAndClave(usuario, clave);
        return opt.orElse(null);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void activar(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.ifPresent(u -> {
            u.setActivo(true);
            usuarioRepository.save(u);
        });
    }

    public void anular(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.ifPresent(u -> {
            u.setActivo(false);
            usuarioRepository.save(u);
        });
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
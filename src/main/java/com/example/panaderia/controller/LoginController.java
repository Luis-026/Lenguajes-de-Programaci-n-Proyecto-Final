package com.example.panaderia.controller;

import com.example.panaderia.entity.Usuario;
import com.example.panaderia.entity.Rol;
import com.example.panaderia.entity.RolPermiso;
import com.example.panaderia.service.UsuarioService;
import com.example.panaderia.service.RolService;
import com.example.panaderia.repository.RolPermisoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String clave,
                                Model model,
                                HttpSession session) {

        Usuario u = usuarioService.validarLogin(usuario, clave);
        if (u == null) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }

        if (!u.isActivo()) {
            model.addAttribute("error", "Usuario desactivado");
            return "login";
        }

        session.setAttribute("usuario", u);

        return "redirect:/principal";
    }

    @GetMapping("/principal")
    public String mostrarPrincipal(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        Rol rol = rolService.buscarPorNombre(usuario.getRol());

        Set<String> permisos = new HashSet<>();

        if (rol != null) {
            List<RolPermiso> listaPermisos = rolPermisoRepository.findByRol(rol);
            permisos = listaPermisos.stream()
                    .map(RolPermiso::getModulo)
                    .collect(Collectors.toSet());
        }

        if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getRol())) {
            permisos.add("USUARIOS");
            permisos.add("PRODUCTOS");
            permisos.add("CATEGORIAS");
            permisos.add("ROLES");
            permisos.add("VENTAS");
            permisos.add("INVENTARIO");
            permisos.add("REPORTES");
        }

        model.addAttribute("permisos", permisos);

        return "principal";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

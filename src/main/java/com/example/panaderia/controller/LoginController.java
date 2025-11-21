package com.example.panaderia.controller;

import com.example.panaderia.entity.Usuario;
import com.example.panaderia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

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
        return "principal";
    }


    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
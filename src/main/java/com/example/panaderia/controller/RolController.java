package com.example.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.panaderia.entity.Rol;
import com.example.panaderia.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.listarTodos());
        return "roles";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol) {
        rolService.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return "redirect:/roles";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        rolService.activar(id);
        return "redirect:/roles";
    }

    @GetMapping("/anular/{id}")
    public String anular(@PathVariable Long id) {
        rolService.anular(id);
        return "redirect:/roles";
    }
}
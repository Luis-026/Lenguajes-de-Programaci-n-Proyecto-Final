package com.example.panaderia.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.panaderia.entity.Rol;
import com.example.panaderia.entity.RolPermiso;
import com.example.panaderia.service.RolService;

import jakarta.transaction.Transactional;

import com.example.panaderia.repository.RolPermisoRepository;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

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

    @GetMapping("/permisos/{id}")
    public String verPermisos(@PathVariable Long id, Model model) {

        Rol rol = rolService.buscarPorId(id);

        List<String> modulos = List.of(
                "USUARIOS",
                "PRODUCTOS",
                "CATEGORIAS",
                "ROLES",
                "VENTAS",
                "INVENTARIO",
                "REPORTES"
        );

        List<RolPermiso> permisosRol = rolPermisoRepository.findByRol(rol);
        Set<String> permisosSeleccionados = permisosRol.stream()
                .map(RolPermiso::getModulo)
                .collect(Collectors.toSet());

        model.addAttribute("rol", rol);
        model.addAttribute("modulos", modulos);
        model.addAttribute("permisos", permisosSeleccionados);

        model.addAttribute("listaRoles", rolService.listarTodos());

        return "permisos";
    }

    @PostMapping("/permisos/guardar")
    @Transactional
    public String guardarPermisos(
            @RequestParam("rolId") Long rolId,
            @RequestParam(value = "modulos", required = false) List<String> modulosSeleccionados) {

        Rol rol = rolService.buscarPorId(rolId);
        rolPermisoRepository.deleteByRol(rol);
        if (modulosSeleccionados != null) {
            for (String modulo : modulosSeleccionados) {
                RolPermiso rp = new RolPermiso();
                rp.setRol(rol);
                rp.setModulo(modulo);
                rolPermisoRepository.save(rp);
            }
        }

        return "redirect:/roles/permisos/" + rolId;
    }

}
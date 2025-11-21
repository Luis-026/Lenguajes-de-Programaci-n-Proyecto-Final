package com.example.panaderia.controller;

import com.example.panaderia.entity.Categoria;
import com.example.panaderia.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(value = "id", required = false) Long id,
                          @RequestParam("nombre") String nombre,
                          @RequestParam(value = "activo", required = false) String activoFlag) {

        Categoria c = (id != null) ? categoriaService.buscarPorId(id) : new Categoria();
        if (c == null) c = new Categoria();

        c.setNombre(nombre);
        c.setActivo(activoFlag != null);

        categoriaService.guardar(c);
        return "redirect:/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        categoriaService.activar(id);
        return "redirect:/categorias";
    }

    @GetMapping("/anular/{id}")
    public String anular(@PathVariable Long id) {
        categoriaService.anular(id);
        return "redirect:/categorias";
    }
}
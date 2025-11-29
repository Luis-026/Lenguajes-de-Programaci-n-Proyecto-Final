package com.example.panaderia.controller;

import com.example.panaderia.service.ProductoService;
import com.example.panaderia.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String inventario(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("ventas", ventaService.listarVentas());

        model.addAttribute("totalStock", productoService.totalStock());
        model.addAttribute("bajoStock", productoService.productosBajoStock());
        model.addAttribute("cantidadProductos", productoService.listarTodos().size());

        return "inventario";
    }
}
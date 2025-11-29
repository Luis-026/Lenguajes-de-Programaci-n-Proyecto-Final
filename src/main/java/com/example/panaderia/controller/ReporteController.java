package com.example.panaderia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.panaderia.service.VentaService;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String verReportes(Model model) {

        double totalMonto = ventaService.obtenerTotalMontoVentas();
        long totalComprobantes = ventaService.obtenerTotalComprobantes();
        long totalProductosVendidos = ventaService.obtenerTotalProductosVendidos();

        List<Object[]> topProductos = ventaService.obtenerProductosMasVendidos();

        model.addAttribute("ventasRecientes", ventaService.listarUltimasVentas());
        model.addAttribute("totalMonto", totalMonto);
        model.addAttribute("totalComprobantes", totalComprobantes);
        model.addAttribute("totalProductosVendidos", totalProductosVendidos);
        model.addAttribute("topProductos", topProductos);

        return "reportes";
    }
}
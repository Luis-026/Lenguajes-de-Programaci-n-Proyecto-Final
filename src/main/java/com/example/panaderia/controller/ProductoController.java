package com.example.panaderia.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.panaderia.entity.Categoria;
import com.example.panaderia.entity.Producto;
import com.example.panaderia.service.CategoriaService;
import com.example.panaderia.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    // Ruta donde se guardarán las imágenes - directorio del proyecto
    @Value("${file.upload-dir:uploads/productos}")
    private String uploadDir;

    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listarTodos();
        List<Categoria> categorias = categoriaService.listarTodas();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "productos"; // productos.html
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          @RequestParam("imagenFile") MultipartFile imagenFile) {

        // Procesar la imagen si se subió una
        if (!imagenFile.isEmpty()) {
            try {
                String nombreImagen = guardarImagen(imagenFile);
                producto.setImagen(nombreImagen);
            } catch (IOException e) {
                // En caso de error, continuar sin imagen
                System.err.println("Error al guardar imagen: " + e.getMessage());
            }
        }

        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        // Obtener el producto para eliminar también su imagen
        Producto producto = productoService.buscarPorId(id);
        if (producto != null && producto.getImagen() != null) {
            eliminarImagen(producto.getImagen());
        }

        productoService.eliminar(id);
        return "redirect:/productos";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        productoService.activar(id);
        return "redirect:/productos";
    }

    @GetMapping("/anular/{id}")
    public String anular(@PathVariable Long id) {
        productoService.anular(id);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Producto editar(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    // Método para guardar la imagen físicamente
    private String guardarImagen(MultipartFile file) throws IOException {
        // Obtener la ruta del directorio del proyecto
        String projectPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(projectPath, uploadDir);

        // Crear el directorio si no existe
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generar nombre único para la imagen
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String nombreUnico = UUID.randomUUID().toString() + "." + extension;

        // Ruta completa del archivo
        Path filePath = uploadPath.resolve(nombreUnico);

        // Copiar el archivo al directorio de destino
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return nombreUnico;
    }

    // Método para eliminar imagen física
    private void eliminarImagen(String nombreImagen) {
        try {
            String projectPath = System.getProperty("user.dir");
            Path imagePath = Paths.get(projectPath, uploadDir).resolve(nombreImagen);
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            System.err.println("Error al eliminar imagen: " + e.getMessage());
        }
    }
}
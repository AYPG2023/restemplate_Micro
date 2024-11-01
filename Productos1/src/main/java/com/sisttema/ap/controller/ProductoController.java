package com.sisttema.ap.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisttema.ap.entity.ApiResponse;
import com.sisttema.ap.entity.Producto;
import com.sisttema.ap.services.IProducto;



@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    protected IProducto productosService;

    // Método para obtener todos los productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> getAllProductos() {
        List<Producto> productos = productosService.findAll();  // Obtiene la lista de productos
        return ResponseEntity.ok(new ApiResponse<>(productos, "Lista de productos", true));  // Retorna la lista en ApiResponse
    }

    // Método para guardar un nuevo producto
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Producto>> save(@RequestBody Producto productos) {
        Producto savedProducto = productosService.save(productos);  // Guarda el producto
        return ResponseEntity.ok(new ApiResponse<>(savedProducto, "Producto agregado correctamente", true));  // Respuesta estructurada
    }

    // Método para actualizar un producto existente
    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Producto>> update(@PathVariable("id") Integer id, @RequestBody Producto productos) {
        Producto updatedProductos = productosService.update(id, productos);  // Actualiza el producto
        if (updatedProductos != null) {
            return ResponseEntity.ok(new ApiResponse<>(updatedProductos, "Producto actualizado exitosamente", true));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "Producto no encontrado", false));
        }
    }

    // Método para eliminar un producto
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Integer id) {
        Integer response = productosService.deleteById(id);  // Intenta eliminar el producto
        if (response == 204) {
            return ResponseEntity.ok(new ApiResponse<>(null, "Producto eliminado exitosamente", true));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "Producto no encontrado", false));
        }
    }
}

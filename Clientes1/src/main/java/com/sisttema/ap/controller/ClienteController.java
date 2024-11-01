package com.sisttema.ap.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sisttema.ap.entity.ApiResponse;
import com.sisttema.ap.entity.Cliente;
import com.sisttema.ap.services.IClientes;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    @Autowired
    protected IClientes clientesService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> getAllClientes() {
        return ResponseEntity.ok(new ApiResponse<>(clientesService.findAll(), "Lista de clientes", true));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Cliente>> save(@Valid @RequestBody Cliente cliente) {
        Cliente savedCliente = clientesService.save(cliente);
        return ResponseEntity.ok(new ApiResponse<>(savedCliente, "Cliente guardado exitosamente", true));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Cliente>> update(@PathVariable("id") Integer id, @Valid @RequestBody Cliente cliente) {
        Cliente updatedCliente = clientesService.update(id, cliente);
        if (updatedCliente != null) {
            return ResponseEntity.ok(new ApiResponse<>(updatedCliente, "Cliente actualizado exitosamente", true));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "Cliente no encontrado", false));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Integer id) {
        Integer response = clientesService.deleteById(id);
        if (response == 204) {
            return ResponseEntity.ok(new ApiResponse<>(null, "Cliente eliminado exitosamente", true));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "Cliente no encontrado", false));
        }
    }
}

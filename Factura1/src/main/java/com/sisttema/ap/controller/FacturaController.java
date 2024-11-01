package com.sisttema.ap.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.sisttema.ap.dto.FacturaDTO;
import com.sisttema.ap.dto.FacturaResponseDTO;
import com.sisttema.ap.dto.ProductoAdquiridoDTO;
import com.sisttema.ap.dto.ProductoFacturaDTO;
import com.sisttema.ap.entity.ApiResponse;
import com.sisttema.ap.entity.Cliente;
import com.sisttema.ap.entity.Factura;
import com.sisttema.ap.entity.FacturaProducto;
import com.sisttema.ap.entity.Producto;
import com.sisttema.ap.repository.FacturaRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String CLIENTE_SERVICE_URL = "http://localhost:8081/clientes/";
    private static final String PRODUCTO_SERVICE_URL = "http://localhost:8082/productos/";

    // Obtener todas las facturas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Factura>>> getAllFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(facturas, "Lista de facturas", true));
    }

    // Obtener factura por ID con productos asociados
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<FacturaResponseDTO>> getFacturaById(@PathVariable("id") Integer id) {
        Optional<Factura> facturaOpt = facturaRepository.findByIdWithProductos(id);
        if (facturaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Factura no encontrada", false));
        }

        Factura factura = facturaOpt.get();
        List<ProductoAdquiridoDTO> productosAdquiridos = new ArrayList<>();

        for (FacturaProducto fp : factura.getProductos()) {
            Producto producto = restTemplate.getForObject(PRODUCTO_SERVICE_URL + fp.getProductoId(), Producto.class);
            if (producto != null) {
                productosAdquiridos.add(new ProductoAdquiridoDTO(
                    producto.getNombre(),
                    producto.getPrecio(),
                    fp.getCantidad()
                ));
            }
        }

        FacturaResponseDTO facturaResponse = new FacturaResponseDTO(
            factura.getId(),
            factura.getCliente().getNombre(),
            factura.getTotal(),
            factura.getFecha(),
            productosAdquiridos,
            factura.getDireccionEmpresa()
        );

        return ResponseEntity.ok(new ApiResponse<>(facturaResponse, "Factura obtenida con éxito", true));
    }

    // Crear nueva factura
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FacturaResponseDTO>> createFactura(@RequestBody FacturaDTO facturaDTO) {
        // Verificar si el cliente existe en el microservicio de clientes
        String clienteUrl = CLIENTE_SERVICE_URL + facturaDTO.getClienteId();
        Cliente cliente = restTemplate.getForObject(clienteUrl, Cliente.class);
        if (cliente == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "El cliente no existe", false));
        }

        if (facturaDTO.getProductos() == null || facturaDTO.getProductos().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "La lista de productos no puede estar vacía", false));
        }

        BigDecimal total = BigDecimal.ZERO;
        Factura nuevaFactura = new Factura();
        nuevaFactura.setCliente(cliente);
        nuevaFactura.setFecha(LocalDateTime.now());
        nuevaFactura.setDireccionEmpresa("1 av 1-69 zona 15 Torre 360 Innova AP");

        List<ProductoAdquiridoDTO> productosAdquiridos = new ArrayList<>();

        for (ProductoFacturaDTO prodFacturaDTO : facturaDTO.getProductos()) {
            // Consultar el producto en el microservicio de productos
            String productoUrl = PRODUCTO_SERVICE_URL + prodFacturaDTO.getProductoId();
            Producto producto = restTemplate.getForObject(productoUrl, Producto.class);
            if (producto == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Producto con ID " + prodFacturaDTO.getProductoId() + " no existe", false));
            }

            if (producto.getStock() < prodFacturaDTO.getCantidad()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Stock insuficiente para el producto: " + producto.getNombre(), false));
            }

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(prodFacturaDTO.getCantidad()));
            total = total.add(subtotal);

            producto.setStock(producto.getStock() - prodFacturaDTO.getCantidad());
            restTemplate.put(productoUrl, producto);  // Actualiza el stock en el microservicio de productos

            productosAdquiridos.add(new ProductoAdquiridoDTO(
                producto.getNombre(),
                producto.getPrecio(),
                prodFacturaDTO.getCantidad()
            ));
        }

        nuevaFactura.setTotal(total.doubleValue());
        Factura facturaGuardada = facturaRepository.save(nuevaFactura);

        FacturaResponseDTO respuestaFactura = new FacturaResponseDTO(
            facturaGuardada.getId(),
            cliente.getNombre(),
            facturaGuardada.getTotal(),
            facturaGuardada.getFecha(),
            productosAdquiridos,
            facturaGuardada.getDireccionEmpresa()
        );

        return ResponseEntity.ok(new ApiResponse<>(respuestaFactura, "Factura creada exitosamente. Cliente: " + cliente.getNombre() + ", Total: " + total, true));
    }

    // Método para obtener todos los productos desde el microservicio de productos
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<Producto>>> getAllProductos() {
        ResponseEntity<Producto[]> response = restTemplate.getForEntity(PRODUCTO_SERVICE_URL, Producto[].class);
        if (response.getBody() == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Error al obtener productos", false));
        }
        List<Producto> productos = List.of(response.getBody());
        return ResponseEntity.ok(new ApiResponse<>(productos, "Lista de productos", true));
    }
}

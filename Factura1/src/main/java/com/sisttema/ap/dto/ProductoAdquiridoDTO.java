package com.sisttema.ap.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoAdquiridoDTO {
    private String nombre;      // Nombre del producto
    private BigDecimal precio;  // Precio del producto
    private Integer cantidad;   // Cantidad adquirida (opcional)
}

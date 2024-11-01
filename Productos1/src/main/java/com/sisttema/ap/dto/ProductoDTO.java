package com.sisttema.ap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer productos_id; // Cambiado a Integer
    private String nombre;
    private double precio;
    private int stock;
}
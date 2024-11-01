package com.sisttema.ap.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoFacturaDTO {
    private Integer productoId;   
    private String nombre;        
    private BigDecimal precio;   
    private Integer stock;        
    private Integer cantidad; 

  
}

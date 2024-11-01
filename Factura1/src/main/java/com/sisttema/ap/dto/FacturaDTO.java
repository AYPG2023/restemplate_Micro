package com.sisttema.ap.dto;

import java.util.List;
import lombok.Data;

@Data
public class FacturaDTO {
    private Integer clienteId;
    private List<ProductoFacturaDTO> productos; 
}
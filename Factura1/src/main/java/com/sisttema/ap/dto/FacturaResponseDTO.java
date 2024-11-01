package com.sisttema.ap.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaResponseDTO {
    private Integer id;
    private String clienteNombre;
    private Double total;
    private LocalDateTime fecha;
    private List<ProductoAdquiridoDTO> productos; // Cambiar de ProductoFacturaDTO a ProductoAdquiridoDTO
    private String direccionEmpresa; 
}

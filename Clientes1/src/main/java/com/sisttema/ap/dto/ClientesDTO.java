package com.sisttema.ap.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDTO {

	private String id;
    private String nombre;
    private String direccion;
    private String telefono;
}

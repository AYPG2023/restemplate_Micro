package com.sisttema.ap.entity;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@javax.persistence.Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiado a IDENTITY para autoincremento
    @Column(name = "id")
    private Integer id; // Cambiado a Integer para autoincremento

    @Column(nullable = false)
    private String nombre;

    @Column(name = "correo_electronico", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String telefono;
}

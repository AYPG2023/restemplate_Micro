package com.sisttema.ap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Entity
@Table(name = "factura_productos")
@IdClass(FacturaProductoId.class) 
@NoArgsConstructor
@AllArgsConstructor
public class FacturaProducto {


	@Id
    @Column(name = "factura_id") // Nombre de columna en la base de datos
    private Integer facturaId;

    @Id
    @Column(name = "producto_id") // Nombre de columna en la base de datos
    private Integer productoId;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Factura factura;  // Relación con Factura

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Producto producto; // Relación con Producto
}

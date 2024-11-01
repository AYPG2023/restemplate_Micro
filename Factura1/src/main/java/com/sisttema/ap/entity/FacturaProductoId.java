package com.sisttema.ap.entity;

import java.io.Serializable;
import java.util.Objects;

public class FacturaProductoId implements Serializable {
    private Integer facturaId;
    private Integer productoId;

    // Constructores, equals y hashCode

    public FacturaProductoId() {}

    public FacturaProductoId(Integer facturaId, Integer productoId) {
        this.facturaId = facturaId;
        this.productoId = productoId;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacturaProductoId)) return false;
        FacturaProductoId that = (FacturaProductoId) o;
        return Objects.equals(facturaId, that.facturaId) && Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facturaId, productoId);
    }
}

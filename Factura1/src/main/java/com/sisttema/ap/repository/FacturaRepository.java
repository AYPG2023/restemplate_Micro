package com.sisttema.ap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisttema.ap.entity.Factura;

import org.springframework.data.repository.query.Param; // Importa el Param correcto



@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
	@Query("SELECT f FROM Factura f WHERE f.id = :facturaId")
	Optional<Factura> findByIdWithProductos(@Param("facturaId") Integer facturaId);
}
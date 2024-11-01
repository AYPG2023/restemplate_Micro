package com.sisttema.ap.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisttema.ap.entity.Factura;


@Service
public interface IFacturaService {
    List<Factura> findAll();
    Factura save(Factura factura);
    Factura findById(Integer id);
    void deleteById(Integer id);
}


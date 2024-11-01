package com.sisttema.ap.services;

import java.util.List;

import com.sisttema.ap.entity.Cliente;


public interface IClientes {
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    Cliente update(Integer id, Cliente cliente);  
    Integer deleteById(Integer id);  
}

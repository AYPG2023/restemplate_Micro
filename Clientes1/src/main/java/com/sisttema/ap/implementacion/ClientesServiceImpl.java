package com.sisttema.ap.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisttema.ap.entity.Cliente;
import com.sisttema.ap.repository.ClienteRepository;
import com.sisttema.ap.services.IClientes;

@Service
public class ClientesServiceImpl implements IClientes {

    @Autowired
    protected ClienteRepository clientesRepository;

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clientesRepository.findAll();
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clientesRepository.save(cliente);
    }

    @Override
    public Cliente update(Integer id, Cliente cliente) {  // Cambiado a Integer
        Optional<Cliente> optionalCliente = clientesRepository.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente existingCliente = optionalCliente.get();
            existingCliente.setNombre(cliente.getNombre());
            existingCliente.setCorreo(cliente.getCorreo());
            existingCliente.setTelefono(cliente.getTelefono());
            return clientesRepository.save(existingCliente);
        }
        return null;  // Devuelve null si no encuentra el cliente
    }

    @Override
    public Integer deleteById(Integer id) {  // Cambiado a Integer
        if (!clientesRepository.existsById(id)) {
            return 404;  // Not Found
        } else {
            clientesRepository.deleteById(id);
            return 204;  // No Content
        }
    }
}

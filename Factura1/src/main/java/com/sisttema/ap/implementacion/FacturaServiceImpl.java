package com.sisttema.ap.implementacion;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisttema.ap.entity.Cliente;
import com.sisttema.ap.entity.Factura;
import com.sisttema.ap.entity.Producto;
import com.sisttema.ap.repository.ClienteRepository;
import com.sisttema.ap.repository.FacturaRepository;
import com.sisttema.ap.repository.ProductoRepository;
import com.sisttema.ap.services.IFacturaService;



@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Factura> findAll() {
        return StreamSupport
            .stream(facturaRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public Factura findById(Integer id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        facturaRepository.deleteById(id);
    }

    public Cliente obtenerCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    public Producto obtenerProducto(Integer idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }
}

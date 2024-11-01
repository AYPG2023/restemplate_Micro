package com.sisttema.ap.implementacion;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisttema.ap.entity.Producto;
import com.sisttema.ap.repository.ProductoRepository;
import com.sisttema.ap.services.IProducto;



@Service
public class ProductoServiceImpl implements IProducto {
    @Autowired
    protected ProductoRepository productosRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productosRepository.findAll();
    }

    @Override
    public Producto save(Producto productos) {
        // El ID se genera automáticamente, no es necesario establecerlo
        return productosRepository.save(productos);
    }

    @Override
    public Producto update(Integer productos_id, Producto productos) { // Usa Integer
        Optional<Producto> optionalProducto = productosRepository.findById(productos_id);
        if (optionalProducto.isPresent()) {
            Producto existingProducto = optionalProducto.get();
            existingProducto.setNombre(productos.getNombre());
            existingProducto.setPrecio(productos.getPrecio());
            existingProducto.setStock(productos.getStock());
            return productosRepository.save(existingProducto);
        }
        return null; // Devuelve null si no se encuentra el producto
    }

    @Override
    public Integer deleteById(Integer productos_id) { // Usa Integer
        if (!productosRepository.existsById(productos_id)) {
            return 404; // Not Found
        } else {
            productosRepository.deleteById(productos_id);
            return 204; // No Content
        }
    }
}

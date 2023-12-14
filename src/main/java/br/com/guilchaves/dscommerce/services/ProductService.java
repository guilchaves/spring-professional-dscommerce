package br.com.guilchaves.dscommerce.services;

import br.com.guilchaves.dscommerce.dto.ProductDTO;
import br.com.guilchaves.dscommerce.entities.Product;
import br.com.guilchaves.dscommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable) {
        Page<Product> result = repository.findByNameContainingIgnoreCase(name, pageable);
        return result.map(ProductDTO::new);
    }
}

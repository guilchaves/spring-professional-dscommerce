package br.com.guilchaves.dscommerce.controllers;

import br.com.guilchaves.dscommerce.dto.ProductDTO;
import br.com.guilchaves.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(name = "name", defaultValue = "")
            String name,
            Pageable pageable) {
        Page<ProductDTO> dto = service.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }


}

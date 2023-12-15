package br.com.guilchaves.dscommerce.services;

import br.com.guilchaves.dscommerce.dto.CategoryDTO;
import br.com.guilchaves.dscommerce.entities.Category;
import br.com.guilchaves.dscommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll(){
        List<Category> result = repository.findAll();
        return result.stream().map(CategoryDTO::new).toList();
    }
}

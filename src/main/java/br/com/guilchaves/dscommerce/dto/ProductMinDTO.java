package br.com.guilchaves.dscommerce.dto;

import br.com.guilchaves.dscommerce.entities.Category;
import br.com.guilchaves.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;
    @NotEmpty(message = "Product must have at least 1 category")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductMinDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductMinDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        for (Category c : entity.getCategories()) {
            categories.add(new CategoryDTO(c));
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}

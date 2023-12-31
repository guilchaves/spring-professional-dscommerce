package br.com.guilchaves.dscommerce.dto;

import br.com.guilchaves.dscommerce.entities.Category;
import br.com.guilchaves.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;
    @Size(min = 3, max = 80, message = "Name must be beetween {min} and {max} characters long")
    @NotBlank(message = "Name is required")
    private String name;
    @Size(min = 10, message = "Description must be at least {min} characters")
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be a positive value")
    @NotNull(message = "Price is required")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Product must have at least 1 category")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

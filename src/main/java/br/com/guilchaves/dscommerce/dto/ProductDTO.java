package br.com.guilchaves.dscommerce.dto;

import br.com.guilchaves.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
}

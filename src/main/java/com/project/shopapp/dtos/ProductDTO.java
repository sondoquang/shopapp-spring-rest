package com.project.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    @NotEmpty(message = "Name product is required!")
    @Size(min = 3, max = 200, message = "Name product must be between 3 and 200 characters !")
    private String name;

    @Min(value=0, message = "Price must be greater than or equal to 0 !")
    @Max(value=10000000, message = "Price must be less than or equal 10,000,000.0 !")
    private Double price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

}

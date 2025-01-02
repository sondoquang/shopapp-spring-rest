package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImageDTO {

    @JsonProperty(value = "product_id")
    @Min(value = 1, message = "Product's ID must be greater than 0")
    private long productId;

    @JsonProperty(value = "image_url")
    @Size(min = 5, max = 200, message = "Image's name must be greater than 5 characters and least than 200 characters !")
    private String imageUrl;

}

package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;



@Data
public class CategoryDTO {

    @NotEmpty(message = "Category's name not is empty !")
    private String name;
}

package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO {

    @NotEmpty(message = "Category's name not is empty !")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

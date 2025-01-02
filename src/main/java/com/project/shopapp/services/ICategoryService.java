package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAllCategory();

    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    Category updateCategory(Long id, CategoryDTO CategoryDTO);

    void deleteCategory(Long id);
}

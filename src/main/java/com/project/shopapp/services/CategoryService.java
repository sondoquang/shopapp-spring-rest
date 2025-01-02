package com.project.shopapp.services;

import com.project.shopapp.Repositories.CategoryRepository;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Annotation tự tạo hàm tạo //
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryDTO CategoryDTO) {
        Category newCategory = Category
                .builder()
                .name(CategoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existCategory = getCategoryById(id);
        existCategory.setName(categoryDTO.getName());
        return categoryRepository.save(existCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        // Xóa cứng //
        categoryRepository.deleteById(id);
    }
}

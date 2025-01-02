package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;

import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("")
    // Nếu request truyền vào là một Object ==> Data transfer object = RequestObject
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                         BindingResult bindingResult) {
        // check has error //
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert Category success");
    }

    // Hiển thị tất cả các category //
    @GetMapping("")// http://localhost:6789/api/v1/categories //
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategory();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
        categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok("Update Category success");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category success");
    }
}

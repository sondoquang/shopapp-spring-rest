package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    // Hiển thị tất cả các category //
    @GetMapping("")// http://localhost:6789/api/v1/categories //
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok("Page: " + page + " Limit: " + limit);
    }

    @PostMapping("")
    // Nếu request truyền vào là một Object ==> Data transfer object = RequestObject
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Category with id: "+ categoryDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id) {
        return ResponseEntity.ok("This is the method category updated" + id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        return ResponseEntity.ok("This is the method category deleted" + id);
    }
}

package com.project.shopapp.controllers;


import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok("get product here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAllCategories(@PathVariable int id) {
        return ResponseEntity.ok("get product here with id: " + id);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // Cho phép upload file //
    public ResponseEntity<?> addCategory(@Valid @ModelAttribute ProductDTO productDTO,
                                         BindingResult result
                                         /*@RequestPart("image") MultipartFile file*/) throws IOException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        List<MultipartFile> files = productDTO.getFiles();
        files = files == null? new ArrayList<>() : files;
        for(MultipartFile file : files) {
            if(files.size() == 0) {
                continue;
            }
            // Kiểm tra kích thước file và định dạng da ok chua //
            if (file.getSize() > 10 * 1024 * 1024) {
                // Kich thuoc phai nho hon 10 MB (10mb - 1024kb - 1024byte)
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                        .body("File is too large !!Maximum size is 10MB !");
            }

            // lay dinh dang file //
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an Image !");
            }

            // Upload file len server va cap nhat url(thumbnail) -> trong dto //
            String fileName = storeFile(file);
            // Lưu vào database -> làm sau //
            // Lưu bảng product_images
        }
        return ResponseEntity.ok("Product added is successfully");
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        /* Thêm UUID Cho filename de file la duy nhat */
        String uniqueFilename = UUID.randomUUID().toString()+"_"+fileName;
        Path uploadDir  = Paths.get("Uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
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

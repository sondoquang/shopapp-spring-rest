package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // check valid product by name //
    boolean existsByName(String name);

    // Hàm dùng để phân trang sản phẩm //
    Page<Product> findAll(Pageable page);
}

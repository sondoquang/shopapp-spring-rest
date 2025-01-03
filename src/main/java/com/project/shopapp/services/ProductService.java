package com.project.shopapp.services;

import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException("Category not found with id: "+ productDTO.getCategoryId()));
        Product product = Product.builder()
                            .name(productDTO.getName())
                            .price(productDTO.getPrice())
                            .thumbnail(productDTO.getThumbnail())
                            .description(productDTO.getDescription())
                            .category(existingCategory)
                            .build();
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException
                        ("Can not find product with id: "+productId));
    }

    @Override
    public ProductResponse getProductByIdV01(Long productId) throws Exception {
        Product product = getProductById(productId);
        if(product != null){
            ProductResponse.fromProduct(product);
        }
        return null;
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable page) {
        // lay danh sach theo page va limit //
        return productRepository.findAll(page).map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            // convert from productDTO --> product
            // Co the su dung object mapper //
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Category not found with id: "+ productDTO.getCategoryId()));
            existingProduct.setCategory(existingCategory);
            existingProduct.setDescription(productDTO.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Product product = getProductById(id);
        if(product != null){
            productRepository.delete(product);
        }
    }

    @Override
    public boolean existByName(String productName) {
        return productRepository.existsByName(productName);
    }

    // Ham them anh cho product //
    @Override
    public ProductImage createProductImage(long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(()->
                        new DataNotFoundException(
                                "Can not find product with id: "+ productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // Khong cho product ton tai tren 5 anh //
        int size = productImageRepository.findByProductId(productId).size();
        if( size >= ProductImage.MAXIMUM_IMAGE){
            throw new InvalidParamException("Number of images must be least than "+ProductImage.MAXIMUM_IMAGE);
        }
        return productImageRepository.save(newProductImage);
    }
}

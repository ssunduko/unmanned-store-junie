package com.unmannedstore.features.shopping_management.api;

import com.unmannedstore.features.shopping_management.api.dto.ProductDto;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for product-related operations.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor for ProductController.
     * 
     * @param productService The product service
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products.
     * 
     * @return A list of all products
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Get a product by its ID.
     * 
     * @param id The product ID
     * @return The product with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        Optional<Product> productOpt = productService.findById(id);
        return productOpt.map(product -> ResponseEntity.ok(convertToDto(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new product.
     * 
     * @param productDto The product to create
     * @return The created product
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedProduct));
    }

    /**
     * Update an existing product.
     * 
     * @param id The product ID
     * @param productDto The updated product data
     * @return The updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        Optional<Product> existingProductOpt = productService.findById(id);
        if (existingProductOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = existingProductOpt.get();
        updateProductFromDto(existingProduct, productDto);

        Product updatedProduct = productService.save(existingProduct);
        return ResponseEntity.ok(convertToDto(updatedProduct));
    }

    /**
     * Delete a product.
     * 
     * @param id The product ID
     * @return No content if successful, not found if the product doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Find products by category.
     * 
     * @param category The product category
     * @return A list of products in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.findByCategory(category);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Convert a Product entity to a ProductDto.
     * 
     * @param product The product entity
     * @return The product DTO
     */
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setItemId(product.getId());  // Set itemId to be the same as productId for consistency
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setRfidTag(product.getRfidTag());
        dto.setImageUrl(product.getImageUrl());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        return dto;
    }

    /**
     * Convert a ProductDto to a Product entity.
     * 
     * @param dto The product DTO
     * @return The product entity
     */
    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getProductId());
        product.setName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setRfidTag(dto.getRfidTag());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    /**
     * Update a Product entity from a ProductDto.
     * 
     * @param product The product entity to update
     * @param dto The product DTO with updated data
     */
    private void updateProductFromDto(Product product, ProductDto dto) {
        product.setName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setRfidTag(dto.getRfidTag());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
    }
}

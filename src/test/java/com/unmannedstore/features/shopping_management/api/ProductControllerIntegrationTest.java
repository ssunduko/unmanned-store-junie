package com.unmannedstore.features.shopping_management.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unmannedstore.features.shopping_management.api.dto.ProductDto;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setId("product-1");
        product1.setName("Test Product 1");
        product1.setPrice(new BigDecimal("10.00"));
        product1.setCategory("Electronics");

        Product product2 = new Product();
        product2.setId("product-2");
        product2.setName("Test Product 2");
        product2.setPrice(new BigDecimal("20.00"));
        product2.setCategory("Clothing");

        List<Product> products = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("product-1"))
                .andExpect(jsonPath("$[0].productName").value("Test Product 1"))
                .andExpect(jsonPath("$[0].itemId").value("product-1"))  // Check that itemId is set correctly
                .andExpect(jsonPath("$[1].productId").value("product-2"))
                .andExpect(jsonPath("$[1].productName").value("Test Product 2"))
                .andExpect(jsonPath("$[1].itemId").value("product-2"));  // Check that itemId is set correctly
    }

    @Test
    public void testGetProductById() throws Exception {
        // Arrange
        String productId = "product-1";

        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setCategory("Electronics");

        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value("Test Product"))
                .andExpect(jsonPath("$.itemId").value(productId));  // Check that itemId is set correctly
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setProductId("product-1");
        productDto.setProductName("Test Product");
        productDto.setPrice(new BigDecimal("10.00"));
        productDto.setCategory("Electronics");

        Product product = new Product();
        product.setId("product-1");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setCategory("Electronics");

        when(productService.save(any(Product.class))).thenReturn(product);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value("product-1"))
                .andExpect(jsonPath("$.productName").value("Test Product"))
                .andExpect(jsonPath("$.itemId").value("product-1"));  // Check that itemId is set correctly
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Arrange
        String productId = "product-1";

        ProductDto productDto = new ProductDto();
        productDto.setProductId(productId);
        productDto.setProductName("Updated Product");
        productDto.setPrice(new BigDecimal("15.00"));
        productDto.setCategory("Electronics");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Test Product");
        existingProduct.setPrice(new BigDecimal("10.00"));
        existingProduct.setCategory("Electronics");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(new BigDecimal("15.00"));
        updatedProduct.setCategory("Electronics");

        when(productService.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productService.save(any(Product.class))).thenReturn(updatedProduct);

        // Act & Assert
        mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(15.00))
                .andExpect(jsonPath("$.itemId").value(productId));  // Check that itemId is set correctly
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange
        String productId = "product-1";

        when(productService.delete(productId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        // Arrange
        String category = "Electronics";

        Product product1 = new Product();
        product1.setId("product-1");
        product1.setName("Test Product 1");
        product1.setPrice(new BigDecimal("10.00"));
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId("product-2");
        product2.setName("Test Product 2");
        product2.setPrice(new BigDecimal("20.00"));
        product2.setCategory(category);

        List<Product> products = Arrays.asList(product1, product2);

        when(productService.findByCategory(category)).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products/category/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("product-1"))
                .andExpect(jsonPath("$[0].productName").value("Test Product 1"))
                .andExpect(jsonPath("$[0].category").value(category))
                .andExpect(jsonPath("$[0].itemId").value("product-1"))  // Check that itemId is set correctly
                .andExpect(jsonPath("$[1].productId").value("product-2"))
                .andExpect(jsonPath("$[1].productName").value("Test Product 2"))
                .andExpect(jsonPath("$[1].category").value(category))
                .andExpect(jsonPath("$[1].itemId").value("product-2"));  // Check that itemId is set correctly
    }
}

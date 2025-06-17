package com.unmannedstore.features.shopping_management.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unmannedstore.UnmannedStoreApplication;
import com.unmannedstore.features.shopping_management.api.dto.ProductDto;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = UnmannedStoreApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ProductControllerFullIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/products";

        // Create a test product
        testProduct = new Product();
        testProduct.setId("test-product-1");
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("10.00"));
        testProduct.setRfidTag("test-rfid-1");
        testProduct.setCategory("Beverages");
        testProduct.setDescription("Test Description");
        testProduct.setImageUrl("https://example.com/test-image.jpg");

        // Save the product to the database
        testProduct = productRepository.save(testProduct);

        // Create additional test products for category testing
        Product product2 = new Product();
        product2.setId("test-product-2");
        product2.setName("Test Product 2");
        product2.setPrice(new BigDecimal("20.00"));
        product2.setRfidTag("test-rfid-2");
        product2.setCategory("Beverages");
        product2.setDescription("Test Description 2");
        product2.setImageUrl("https://example.com/test-image-2.jpg");
        productRepository.save(product2);
    }

    @Test
    public void testGetAllProducts() {
        // Make the request
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProductDto> products = response.getBody();
        assertNotNull(products);
        assertFalse(products.isEmpty());

        // Verify that our test products are present
        assertTrue(products.stream().anyMatch(p -> p.getProductId().equals("test-product-1")));
        assertTrue(products.stream().anyMatch(p -> p.getProductId().equals("test-product-2")));

        // Verify that itemId is set correctly
        products.forEach(p -> assertEquals(p.getProductId(), p.getItemId()));
    }

    @Test
    public void testGetProductById() {
        // Make the request
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(
                baseUrl + "/" + testProduct.getId(),
                ProductDto.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductDto product = response.getBody();
        assertNotNull(product);
        assertEquals(testProduct.getId(), product.getProductId());
        assertEquals(testProduct.getName(), product.getProductName());
        assertEquals(testProduct.getId(), product.getItemId());
    }

    @Test
    public void testCreateProduct() {
        // Create a new product DTO
        ProductDto newProductDto = new ProductDto();
        newProductDto.setProductId(UUID.randomUUID().toString());
        newProductDto.setProductName("Test New Product");
        newProductDto.setPrice(new BigDecimal("12.99"));
        newProductDto.setCategory("Test Category");
        newProductDto.setRfidTag("test-rfid-tag");

        // Make the request
        ResponseEntity<ProductDto> response = restTemplate.postForEntity(
                baseUrl,
                newProductDto,
                ProductDto.class);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ProductDto createdProduct = response.getBody();
        assertNotNull(createdProduct);
        assertEquals(newProductDto.getProductId(), createdProduct.getProductId());
        assertEquals(newProductDto.getProductName(), createdProduct.getProductName());
        assertEquals(newProductDto.getPrice(), createdProduct.getPrice());
        assertEquals(newProductDto.getCategory(), createdProduct.getCategory());
        assertEquals(newProductDto.getRfidTag(), createdProduct.getRfidTag());
        assertEquals(newProductDto.getProductId(), createdProduct.getItemId());

        // Verify the database was updated
        Optional<Product> savedProduct = productRepository.findById(newProductDto.getProductId());
        assertTrue(savedProduct.isPresent());
        assertEquals(newProductDto.getProductName(), savedProduct.get().getName());
        assertEquals(newProductDto.getPrice(), savedProduct.get().getPrice());
        assertEquals(newProductDto.getCategory(), savedProduct.get().getCategory());
        assertEquals(newProductDto.getRfidTag(), savedProduct.get().getRfidTag());
    }

    @Test
    public void testUpdateProduct() {
        // Create a product to update
        Product productToUpdate = new Product();
        productToUpdate.setId(UUID.randomUUID().toString());
        productToUpdate.setName("Original Name");
        productToUpdate.setPrice(new BigDecimal("9.99"));
        productToUpdate.setCategory("Original Category");
        productToUpdate.setRfidTag("original-rfid");
        productRepository.save(productToUpdate);

        // Create the update DTO
        ProductDto updateDto = new ProductDto();
        updateDto.setProductId(productToUpdate.getId());
        updateDto.setProductName("Updated Name");
        updateDto.setPrice(new BigDecimal("19.99"));
        updateDto.setCategory("Updated Category");
        updateDto.setRfidTag("updated-rfid");

        // Make the request
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                baseUrl + "/" + productToUpdate.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ProductDto.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductDto updatedProduct = response.getBody();
        assertNotNull(updatedProduct);
        assertEquals(updateDto.getProductId(), updatedProduct.getProductId());
        assertEquals(updateDto.getProductName(), updatedProduct.getProductName());
        assertEquals(updateDto.getPrice(), updatedProduct.getPrice());
        assertEquals(updateDto.getCategory(), updatedProduct.getCategory());
        assertEquals(updateDto.getRfidTag(), updatedProduct.getRfidTag());
        assertEquals(updateDto.getProductId(), updatedProduct.getItemId());

        // Verify the database was updated
        Optional<Product> savedProduct = productRepository.findById(productToUpdate.getId());
        assertTrue(savedProduct.isPresent());
        assertEquals(updateDto.getProductName(), savedProduct.get().getName());
        assertEquals(updateDto.getPrice(), savedProduct.get().getPrice());
        assertEquals(updateDto.getCategory(), savedProduct.get().getCategory());
        assertEquals(updateDto.getRfidTag(), savedProduct.get().getRfidTag());
    }

    @Test
    public void testDeleteProduct() {
        // Create a product to delete
        Product productToDelete = new Product();
        productToDelete.setId(UUID.randomUUID().toString());
        productToDelete.setName("Product to Delete");
        productToDelete.setPrice(new BigDecimal("5.99"));
        productToDelete.setRfidTag("test-rfid-delete");
        productRepository.save(productToDelete);

        // Make the request
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + productToDelete.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify the database was updated
        Optional<Product> deletedProduct = productRepository.findById(productToDelete.getId());
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    public void testGetProductsByCategory() {
        // Make the request
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                baseUrl + "/category/Beverages",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProductDto> products = response.getBody();
        assertNotNull(products);
        assertFalse(products.isEmpty());

        // Verify that all products have the correct category
        products.forEach(p -> assertEquals("Beverages", p.getCategory()));

        // Verify that our test products are present
        assertTrue(products.stream().anyMatch(p -> p.getProductId().equals("test-product-1")));
        assertTrue(products.stream().anyMatch(p -> p.getProductId().equals("test-product-2")));

        // Verify that itemId is set correctly
        products.forEach(p -> assertEquals(p.getProductId(), p.getItemId()));
    }
}

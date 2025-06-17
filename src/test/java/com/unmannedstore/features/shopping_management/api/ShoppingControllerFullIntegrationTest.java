package com.unmannedstore.features.shopping_management.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unmannedstore.UnmannedStoreApplication;
import com.unmannedstore.features.shopping_management.api.dto.BasketContentsResponse;
import com.unmannedstore.features.shopping_management.api.dto.BasketUpdateResponse;
import com.unmannedstore.features.shopping_management.api.dto.ItemAddRequest;
import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = UnmannedStoreApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ShoppingControllerFullIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingSessionRepository shoppingSessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    private ShoppingSession testSession;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/stores/1/baskets/";

        // Create a test product
        testProduct = new Product();
        testProduct.setId("test-product-1");
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("10.00"));
        testProduct.setRfidTag("test-rfid-1");
        testProduct.setCategory("Test Category");
        testProduct.setDescription("Test Description");
        testProduct.setImageUrl("https://example.com/test-image.jpg");

        // Save the product to the database
        testProduct = productRepository.save(testProduct);

        // Create a test shopping session with a unique basketId
        String uniqueBasketId = "test-basket-" + UUID.randomUUID().toString();
        testSession = new ShoppingSession();
        testSession.setId(UUID.randomUUID().toString());
        testSession.setBasketId(uniqueBasketId);
        testSession.setStoreId("1");
        testSession.setCustomerId("test-customer-1");
        testSession.setStartedAt(LocalDateTime.now());
        testSession.setLastUpdatedAt(LocalDateTime.now());
        testSession.setStatus("ACTIVE");

        // Save the session to the database
        testSession = shoppingSessionRepository.save(testSession);
    }

    @Test
    public void testGetBasketContents() {
        // Add an item to the session
        testSession.addItem(testProduct);
        testSession = shoppingSessionRepository.save(testSession);

        // Log the request URL
        String requestUrl = baseUrl + testSession.getBasketId() + "/items";
        System.out.println("[DEBUG_LOG] GET Request URL: " + requestUrl);

        // Make the request
        ResponseEntity<BasketContentsResponse> response = restTemplate.getForEntity(
                requestUrl,
                BasketContentsResponse.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BasketContentsResponse contents = response.getBody();
        assertNotNull(contents);
        assertEquals(testSession.getBasketId(), contents.getBasketId());
        assertEquals(1, contents.getItems().size());
        assertEquals(testProduct.getId(), contents.getItems().get(0).getProductId());
        assertEquals(testProduct.getName(), contents.getItems().get(0).getProductName());
    }

    @Test
    public void testAddItemToBasket() {
        // Create the request
        ItemAddRequest request = new ItemAddRequest();
        request.setProductId(testProduct.getId());
        request.setDetectedAt(LocalDateTime.now());

        // Log the request URL and JSON body
        String requestUrl = baseUrl + testSession.getBasketId() + "/items";
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            System.out.println("[DEBUG_LOG] POST Request URL: " + requestUrl);
            System.out.println("[DEBUG_LOG] POST Request Body: " + requestJson);
        } catch (Exception e) {
            System.err.println("[DEBUG_LOG] Error serializing request: " + e.getMessage());
        }

        // Make the request
        ResponseEntity<BasketUpdateResponse> response = restTemplate.postForEntity(
                requestUrl,
                request,
                BasketUpdateResponse.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BasketUpdateResponse updateResponse = response.getBody();
        assertNotNull(updateResponse);
        assertEquals(testSession.getBasketId(), updateResponse.getBasketId());
        assertEquals("item_added", updateResponse.getAction());
        assertEquals(testProduct.getId(), updateResponse.getItem().getProductId());

        // Verify the database was updated
        Optional<ShoppingSession> updatedSession = shoppingSessionRepository.findById(testSession.getId());
        assertTrue(updatedSession.isPresent());
        assertEquals(1, updatedSession.get().getItems().size());
        assertEquals(testProduct.getId(), updatedSession.get().getItems().get(0).getProduct().getId());
    }

    @Test
    public void testRemoveItemFromBasket() {
        // Add an item to the session
        BasketItem item = testSession.addItem(testProduct);
        testSession = shoppingSessionRepository.save(testSession);

        // Log the request URL
        String requestUrl = baseUrl + testSession.getBasketId() + "/items/" + item.getId();
        System.out.println("[DEBUG_LOG] DELETE Request URL: " + requestUrl);

        // Make the request
        ResponseEntity<BasketUpdateResponse> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                null,
                BasketUpdateResponse.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BasketUpdateResponse updateResponse = response.getBody();
        assertNotNull(updateResponse);
        assertEquals(testSession.getBasketId(), updateResponse.getBasketId());
        assertEquals("item_removed", updateResponse.getAction());
        assertEquals(testProduct.getId(), updateResponse.getItem().getProductId());

        // Verify the database was updated
        Optional<ShoppingSession> updatedSession = shoppingSessionRepository.findById(testSession.getId());
        assertTrue(updatedSession.isPresent());
        assertEquals(0, updatedSession.get().getItems().size());
    }

    @Test
    public void testNonApiEndpointIsNotAvailable() {
        // Log the request URL
        String requestUrl = "http://localhost:" + port + "/stores/1/baskets/" + testSession.getBasketId() + "/items";
        System.out.println("[DEBUG_LOG] GET Request URL (non-API endpoint): " + requestUrl);

        // Make the request to the non-API endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(
                requestUrl,
                String.class);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Display all JSON requests for ShoppingController")
    public void displayAllJsonRequests() throws Exception {
        // Create a test product and session
        Product product = new Product();
        product.setId("display-product-1");
        product.setName("Display Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setRfidTag("display-rfid-1");

        ShoppingSession session = new ShoppingSession();
        session.setId("display-session-1");
        session.setBasketId("display-basket-1");

        // 1. GET basket contents request
        String getUrl = baseUrl + testSession.getBasketId() + "/items";
        System.out.println("[DEBUG_LOG] 1. GET Request URL: " + getUrl);

        // 2. POST add item request
        String postUrl = baseUrl + testSession.getBasketId() + "/items";
        ItemAddRequest addRequest = new ItemAddRequest();
        addRequest.setProductId(testProduct.getId());
        addRequest.setDetectedAt(LocalDateTime.now());
        String addRequestJson = objectMapper.writeValueAsString(addRequest);
        System.out.println("[DEBUG_LOG] 2. POST Request URL: " + postUrl);
        System.out.println("[DEBUG_LOG] 2. POST Request Body: " + addRequestJson);

        // 3. DELETE remove item request
        BasketItem item = new BasketItem();
        item.setId("display-item-1");
        String deleteUrl = baseUrl + testSession.getBasketId() + "/items/" + item.getId();
        System.out.println("[DEBUG_LOG] 3. DELETE Request URL: " + deleteUrl);

        // 4. GET non-API endpoint request
        String nonApiUrl = "http://localhost:" + port + "/stores/1/baskets/" + testSession.getBasketId() + "/items";
        System.out.println("[DEBUG_LOG] 4. GET Request URL (non-API endpoint): " + nonApiUrl);

        // This test doesn't actually make any HTTP requests, it just displays the JSON
        assertTrue(true, "This test just displays the JSON requests");
    }
}

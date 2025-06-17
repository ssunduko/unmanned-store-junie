package com.unmannedstore.features.shopping_management.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unmannedstore.features.shopping_management.api.dto.ItemAddRequest;
import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingController.class)
public class ShoppingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShoppingService shoppingService;

    @Test
    public void testGetBasketContents() throws Exception {
        // Arrange
        String storeId = "1";
        String basketId = "1";

        Product product = new Product();
        product.setId("product-1");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setRfidTag("rfid-1");

        BasketItem basketItem = new BasketItem();
        basketItem.setId("item-1");
        basketItem.setProduct(product);
        basketItem.setQuantity(1);

        List<BasketItem> items = new ArrayList<>();
        items.add(basketItem);

        ShoppingSession session = new ShoppingSession();
        session.setId("session-1");
        session.setBasketId(basketId);
        session.setItems(items);

        when(shoppingService.findSessionByBasketId(basketId)).thenReturn(Optional.of(session));

        // Act & Assert
        mockMvc.perform(get("/api/stores/{storeId}/baskets/{basketId}/items", storeId, basketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").value(basketId))
                .andExpect(jsonPath("$.items[0].productId").value("product-1"))
                .andExpect(jsonPath("$.items[0].productName").value("Test Product"));
    }

    @Test
    public void testAddItemToBasket() throws Exception {
        // Arrange
        String storeId = "1";
        String basketId = "1";

        Product product = new Product();
        product.setId("product-1");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setRfidTag("rfid-1");

        BasketItem basketItem = new BasketItem();
        basketItem.setId("item-1");
        basketItem.setProduct(product);
        basketItem.setQuantity(1);

        List<BasketItem> items = new ArrayList<>();
        items.add(basketItem);

        ShoppingSession session = new ShoppingSession();
        session.setId("session-1");
        session.setBasketId(basketId);
        session.setItems(items);

        ItemAddRequest request = new ItemAddRequest();
        request.setProductId("product-1");
        request.setDetectedAt(LocalDateTime.now());

        when(shoppingService.findSessionByBasketId(basketId)).thenReturn(Optional.of(session));
        when(shoppingService.addItemToSession(anyString(), anyString())).thenReturn(session);

        // Act & Assert
        mockMvc.perform(post("/api/stores/{storeId}/baskets/{basketId}/items", storeId, basketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").value(basketId))
                .andExpect(jsonPath("$.action").value("item_added"))
                .andExpect(jsonPath("$.item.productId").value("product-1"));
    }

    @Test
    public void testRemoveItemFromBasket() throws Exception {
        // Arrange
        String storeId = "1";
        String basketId = "1";
        String itemId = "item-1";

        Product product = new Product();
        product.setId("product-1");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        product.setRfidTag("rfid-1");

        BasketItem basketItem = new BasketItem();
        basketItem.setId(itemId);
        basketItem.setProduct(product);
        basketItem.setQuantity(1);

        List<BasketItem> items = new ArrayList<>();
        items.add(basketItem);

        ShoppingSession session = new ShoppingSession();
        session.setId("session-1");
        session.setBasketId(basketId);
        session.setItems(items);

        when(shoppingService.findSessionByBasketId(basketId)).thenReturn(Optional.of(session));
        when(shoppingService.removeItemFromSession(anyString(), anyString())).thenReturn(session);

        // Act & Assert
        mockMvc.perform(delete("/api/stores/{storeId}/baskets/{basketId}/items/{itemId}", storeId, basketId, itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").value(basketId))
                .andExpect(jsonPath("$.action").value("item_removed"))
                .andExpect(jsonPath("$.item.productId").value("product-1"));
    }

    @Test
    public void testNonApiEndpointIsNotAvailable() throws Exception {
        // Arrange
        String storeId = "1";
        String basketId = "1";

        // Act & Assert - This should return 404 since we removed the non-API endpoint
        mockMvc.perform(get("/stores/{storeId}/baskets/{basketId}/items", storeId, basketId))
                .andExpect(status().isNotFound());
    }
}

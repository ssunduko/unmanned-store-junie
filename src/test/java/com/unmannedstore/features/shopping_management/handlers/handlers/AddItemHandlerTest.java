package com.unmannedstore.features.shopping_management.handlers.handlers;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.RunningTotal;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import com.unmannedstore.features.shopping_management.events.ItemAddedEvent;
import com.unmannedstore.features.shopping_management.handlers.commands.AddItemCommand;
import com.unmannedstore.shared.events.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddItemHandlerTest {

    @Mock
    private ShoppingService shoppingService;

    @Mock
    private EventPublisher eventPublisher;

    private AddItemHandler addItemHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addItemHandler = new AddItemHandler(shoppingService, eventPublisher);
    }

    @Test
    void handleShouldAddItemAndPublishEvent() {
        // Arrange
        String sessionId = "session-123";
        String productId = "product-456";
        String basketId = "basket-789";
        
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));
        
        BasketItem basketItem = new BasketItem();
        basketItem.setId("item-123");
        basketItem.setProduct(product);
        basketItem.setQuantity(1);
        
        List<BasketItem> items = new ArrayList<>();
        items.add(basketItem);
        
        ShoppingSession session = new ShoppingSession();
        session.setId(sessionId);
        session.setBasketId(basketId);
        session.setItems(items);
        session.setRunningTotal(new RunningTotal(new BigDecimal("10.00")));
        
        AddItemCommand command = new AddItemCommand(sessionId, productId);
        
        when(shoppingService.addItemToSession(sessionId, productId)).thenReturn(session);
        
        // Act
        ShoppingSession result = addItemHandler.handle(command);
        
        // Assert
        assertEquals(sessionId, result.getId());
        assertEquals(basketId, result.getBasketId());
        assertEquals(1, result.getItems().size());
        
        verify(shoppingService).addItemToSession(sessionId, productId);
        verify(eventPublisher).publish(any(ItemAddedEvent.class));
    }
}
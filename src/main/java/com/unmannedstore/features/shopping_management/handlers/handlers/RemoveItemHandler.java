package com.unmannedstore.features.shopping_management.handlers.handlers;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import com.unmannedstore.features.shopping_management.events.ItemRemovedEvent;
import com.unmannedstore.features.shopping_management.handlers.commands.RemoveItemCommand;
import com.unmannedstore.shared.events.EventPublisher;
import org.springframework.stereotype.Component;

/**
 * Handler for RemoveItemCommand.
 */
@Component
public class RemoveItemHandler {
    
    private final ShoppingService shoppingService;
    private final EventPublisher eventPublisher;
    
    /**
     * Constructor for RemoveItemHandler.
     * 
     * @param shoppingService The shopping service
     * @param eventPublisher The event publisher
     */
    public RemoveItemHandler(ShoppingService shoppingService, EventPublisher eventPublisher) {
        this.shoppingService = shoppingService;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Handle the RemoveItemCommand.
     * 
     * @param command The command to handle
     * @return The updated shopping session
     */
    public ShoppingSession handle(RemoveItemCommand command) {
        // Find the item before removing it to get its product ID
        ShoppingSession session = shoppingService.findSessionById(command.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + command.getSessionId()));
        
        String productId = null;
        for (BasketItem item : session.getItems()) {
            if (item.getId().equals(command.getItemId())) {
                productId = item.getProduct().getId();
                break;
            }
        }
        
        if (productId == null) {
            throw new IllegalArgumentException("Item not found in session: " + command.getItemId());
        }
        
        // Remove the item
        ShoppingSession updatedSession = shoppingService.removeItemFromSession(
                command.getSessionId(),
                command.getItemId()
        );
        
        // Publish event
        eventPublisher.publish(new ItemRemovedEvent(
                updatedSession.getBasketId(),
                productId,
                command.getItemId(),
                updatedSession.getRunningTotal().getTotal()
        ));
        
        return updatedSession;
    }
}
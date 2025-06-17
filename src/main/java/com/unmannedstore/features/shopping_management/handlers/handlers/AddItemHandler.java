package com.unmannedstore.features.shopping_management.handlers.handlers;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import com.unmannedstore.features.shopping_management.events.ItemAddedEvent;
import com.unmannedstore.features.shopping_management.handlers.commands.AddItemCommand;
import com.unmannedstore.shared.events.EventPublisher;
import org.springframework.stereotype.Component;

/**
 * Handler for AddItemCommand.
 */
@Component
public class AddItemHandler {

    private final ShoppingService shoppingService;
    private final EventPublisher eventPublisher;

    /**
     * Constructor for AddItemHandler.
     * 
     * @param shoppingService The shopping service
     * @param eventPublisher The event publisher
     */
    public AddItemHandler(ShoppingService shoppingService, EventPublisher eventPublisher) {
        this.shoppingService = shoppingService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handle the AddItemCommand.
     * 
     * @param command The command to handle
     * @return The updated shopping session
     */
    public ShoppingSession handle(AddItemCommand command) {
        ShoppingSession updatedSession;

        // Add item by RFID tag or product ID
        if (command.usesRfidTag()) {
            updatedSession = shoppingService.addItemToSessionByRfidTag(
                    command.getSessionId(),
                    command.getRfidTag()
            );
        } else {
            updatedSession = shoppingService.addItemToSession(
                    command.getSessionId(),
                    command.getProductId()
            );
        }

        // Get the last added item
        BasketItem lastAddedItem = updatedSession.getItems().get(updatedSession.getItems().size() - 1);

        // Publish event
        eventPublisher.publish(new ItemAddedEvent(
                updatedSession.getBasketId(),
                lastAddedItem.getProduct().getId(),
                lastAddedItem.getId(),
                updatedSession.getRunningTotal().getTotal()
        ));

        return updatedSession;
    }
}

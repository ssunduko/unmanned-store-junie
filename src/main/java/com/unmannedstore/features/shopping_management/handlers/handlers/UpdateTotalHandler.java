package com.unmannedstore.features.shopping_management.handlers.handlers;

import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.PricingService;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import com.unmannedstore.features.shopping_management.events.TotalUpdatedEvent;
import com.unmannedstore.features.shopping_management.handlers.commands.UpdateTotalCommand;
import com.unmannedstore.shared.events.EventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Handler for UpdateTotalCommand.
 */
@Component
public class UpdateTotalHandler {
    
    private final ShoppingService shoppingService;
    private final PricingService pricingService;
    private final EventPublisher eventPublisher;
    
    /**
     * Constructor for UpdateTotalHandler.
     * 
     * @param shoppingService The shopping service
     * @param pricingService The pricing service
     * @param eventPublisher The event publisher
     */
    public UpdateTotalHandler(ShoppingService shoppingService, PricingService pricingService, EventPublisher eventPublisher) {
        this.shoppingService = shoppingService;
        this.pricingService = pricingService;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Handle the UpdateTotalCommand.
     * 
     * @param command The command to handle
     * @return The updated shopping session
     */
    public ShoppingSession handle(UpdateTotalCommand command) {
        // Find the session
        ShoppingSession session = shoppingService.findSessionById(command.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + command.getSessionId()));
        
        // Store the old total for the event
        BigDecimal oldTotal = session.getRunningTotal().getTotal();
        
        // Update the running total
        ShoppingSession updatedSession = pricingService.updateRunningTotal(session);
        
        // Publish event
        eventPublisher.publish(new TotalUpdatedEvent(
                updatedSession.getBasketId(),
                oldTotal,
                updatedSession.getRunningTotal().getTotal(),
                updatedSession.getItemCount()
        ));
        
        return updatedSession;
    }
}
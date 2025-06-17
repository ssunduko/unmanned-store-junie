package com.unmannedstore.features.shopping_management.handlers.commands;

/**
 * Command for removing an item from a shopping session.
 */
public class RemoveItemCommand {
    
    private final String sessionId;
    private final String itemId;
    
    /**
     * Constructor for RemoveItemCommand.
     * 
     * @param sessionId The shopping session ID
     * @param itemId The basket item ID
     */
    public RemoveItemCommand(String sessionId, String itemId) {
        this.sessionId = sessionId;
        this.itemId = itemId;
    }
    
    /**
     * Get the shopping session ID.
     * 
     * @return The shopping session ID
     */
    public String getSessionId() {
        return sessionId;
    }
    
    /**
     * Get the basket item ID.
     * 
     * @return The basket item ID
     */
    public String getItemId() {
        return itemId;
    }
}
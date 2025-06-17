package com.unmannedstore.features.shopping_management.handlers.commands;

/**
 * Command for updating the running total of a shopping session.
 */
public class UpdateTotalCommand {
    
    private final String sessionId;
    
    /**
     * Constructor for UpdateTotalCommand.
     * 
     * @param sessionId The shopping session ID
     */
    public UpdateTotalCommand(String sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * Get the shopping session ID.
     * 
     * @return The shopping session ID
     */
    public String getSessionId() {
        return sessionId;
    }
}
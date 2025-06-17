package com.unmannedstore.features.shopping_management.handlers.commands;

/**
 * Command for adding an item to a shopping session.
 */
public class AddItemCommand {
    
    private final String sessionId;
    private final String productId;
    private final String rfidTag;
    
    /**
     * Constructor for AddItemCommand with product ID.
     * 
     * @param sessionId The shopping session ID
     * @param productId The product ID
     */
    public AddItemCommand(String sessionId, String productId) {
        this.sessionId = sessionId;
        this.productId = productId;
        this.rfidTag = null;
    }
    
    /**
     * Constructor for AddItemCommand with RFID tag.
     * 
     * @param sessionId The shopping session ID
     * @param rfidTag The RFID tag
     * @param useRfid Flag to indicate that RFID tag should be used (to disambiguate constructors)
     */
    public AddItemCommand(String sessionId, String rfidTag, boolean useRfid) {
        this.sessionId = sessionId;
        this.productId = null;
        this.rfidTag = rfidTag;
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
     * Get the product ID.
     * 
     * @return The product ID
     */
    public String getProductId() {
        return productId;
    }
    
    /**
     * Get the RFID tag.
     * 
     * @return The RFID tag
     */
    public String getRfidTag() {
        return rfidTag;
    }
    
    /**
     * Check if this command uses RFID tag.
     * 
     * @return true if RFID tag is used, false otherwise
     */
    public boolean usesRfidTag() {
        return rfidTag != null && !rfidTag.isEmpty();
    }
}
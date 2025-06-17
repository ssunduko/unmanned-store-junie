package com.unmannedstore.shared.events;

/**
 * Interface for publishing events across the application.
 * This is used by handlers to publish domain events when state changes occur.
 */
public interface EventPublisher {
    
    /**
     * Publish an event to all subscribers.
     * 
     * @param event The event to publish
     * @param <T> The type of the event
     */
    <T> void publish(T event);
}
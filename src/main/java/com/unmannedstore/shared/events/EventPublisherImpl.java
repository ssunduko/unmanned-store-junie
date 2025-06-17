package com.unmannedstore.shared.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Implementation of the EventPublisher interface that uses Spring's ApplicationEventPublisher.
 * This class is responsible for publishing events to all subscribers in the application.
 */
@Component
public class EventPublisherImpl implements EventPublisher {
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    /**
     * Constructor for EventPublisherImpl.
     * 
     * @param applicationEventPublisher Spring's event publisher
     */
    public EventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    /**
     * Publish an event to all subscribers.
     * 
     * @param event The event to publish
     * @param <T> The type of the event
     */
    @Override
    public <T> void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
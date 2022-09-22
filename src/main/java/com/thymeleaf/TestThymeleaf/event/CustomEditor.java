package com.thymeleaf.TestThymeleaf.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

// import java.awt.*;

// @Component
public class CustomEditor {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event. ");
        CustomEvent customSpringEvent = new CustomEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
    
}

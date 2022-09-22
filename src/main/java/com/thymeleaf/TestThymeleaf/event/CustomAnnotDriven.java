package com.thymeleaf.TestThymeleaf.event;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

public class CustomAnnotDriven {
    @EventListener
    public void handleContextStart(ContextStartedEvent cse) {
        System.out.println("Handling context started event.");
    }
}

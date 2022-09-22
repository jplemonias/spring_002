package com.thymeleaf.TestThymeleaf.event;

import org.springframework.context.ApplicationListener;

public class CustomListener  implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());
    }
}

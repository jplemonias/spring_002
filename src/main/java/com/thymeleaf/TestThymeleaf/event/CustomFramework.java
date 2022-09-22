package com.thymeleaf.TestThymeleaf.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class CustomFramework implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent cse) {
            System.out.println("Handling context re-freshed event. ");
        }
}

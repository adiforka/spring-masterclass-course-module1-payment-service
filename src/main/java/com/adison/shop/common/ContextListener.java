package com.adison.shop.common;

import lombok.extern.java.Log;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log
@Component
public class ContextListener {

    //supposedly all it takes to put the event-listening and execution of the method below into a separate thread
    @Async
    @EventListener
    //the update method in Observer in the Observer pattern
    public void onContextRefreshed(ContextRefreshedEvent refreshedEvent) {
        log.info("Spring Application Context refreshed");
    }
}

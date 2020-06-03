package com.rubenskj.core.test;

import com.rubenskj.core.entity.Subscribe;
import com.rubenskj.core.interfaces.ICallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Invoice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Invoice.class);

    public void produce() {
        LOGGER.info("Creating a subscriber");
        Subscribe subscribe = new Subscribe(Invoice.class.getSimpleName(), 4, this.handleCallback("dffdgsjk"));

        LOGGER.info("Subscribing");
        subscribe.subscribe();
    }

    public ICallback handleCallback(String email) {
        return () -> {
            LOGGER.info("Handling callback -> {}", email);
            throw new IllegalArgumentException("Testing fallback Invoice");
        };
    }
}

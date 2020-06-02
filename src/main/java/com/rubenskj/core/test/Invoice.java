package com.rubenskj.core.test;

import com.rubenskj.core.entity.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Invoice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Invoice.class);

    public void produce() {
        LOGGER.info("Creating a subscriber");
        Subscribe subscribe = new Subscribe(Invoice.class.getSimpleName(), 3, this::handleCallback, false);

        LOGGER.info("Subscribing");
        subscribe.subscribe();
    }

    public void handleCallback() {
        LOGGER.info("Handling callback");
        throw new IllegalArgumentException("Testing fallback Invoice");
    }
}

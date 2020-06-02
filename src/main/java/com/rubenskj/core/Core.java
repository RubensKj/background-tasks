package com.rubenskj.core;

import com.rubenskj.core.test.EnviaEmailContact;
import com.rubenskj.core.test.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.rubenskj.core.handler.Subscribers.SUBSCRIBERS;

public class Core {

    private static final Logger LOGGER = LoggerFactory.getLogger(Core.class);

    public static void main(String[] args) {
        LOGGER.info("Testing");

        LOGGER.info("SUBSCRIBERS -> {}", SUBSCRIBERS);

        new Invoice().produce();

        LOGGER.info("SUBSCRIBERS -> {}", SUBSCRIBERS);
        new EnviaEmailContact().enviaEmail();


        LOGGER.info("SUBSCRIBERS -> {}", SUBSCRIBERS);
    }
}

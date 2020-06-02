package com.rubenskj.core;

import com.rubenskj.core.test.EnviaEmailContact;
import com.rubenskj.core.test.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {

    private static final Logger LOGGER = LoggerFactory.getLogger(Core.class);

    public static void main(String[] args) {
        LOGGER.info("Testing");


        new Invoice().produce();

        new EnviaEmailContact().enviaEmail();

    }
}

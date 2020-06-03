package com.rubenskj.core.test;

import com.rubenskj.core.entity.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnviaEmailContact {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnviaEmailContact.class);

    public void enviaEmail() {
        new Subscribe(EnviaEmailContact.class.getSimpleName(), 2, () -> {
            LOGGER.info("Enviando email");
            throw new IllegalArgumentException("Testing fallback in EnvioEmail");
        }, 3).subscribe();
    }
}

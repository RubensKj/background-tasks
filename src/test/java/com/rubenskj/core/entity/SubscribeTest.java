package com.rubenskj.core.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubscribeTest {

    @Test
    public void getId() {
        Subscribe subscribe = new Subscribe(SubscribeTest.class.getName(), () -> {
        });

        assertEquals(UUID.fromString(subscribe.getId()).toString(), subscribe.getId());
    }

    @Test
    public void getSubscribeName() {
        Subscribe subscribe = new Subscribe(SubscribeTest.class.getName(), () -> {
        });

        assertEquals(SubscribeTest.class.getName(), subscribe.getSubscribeName());
    }

    @Test
    public void subscribe() throws InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Subscribe subscribe = new Subscribe(SubscribeTest.class.getName(), () -> atomicBoolean.set(true));

        subscribe.subscribe();

        Thread.sleep(100);

        assertTrue(atomicBoolean.get());
    }

    @Test
    void retries() throws InterruptedException {
        AtomicInteger retryConscructor = new AtomicInteger(3);

        Subscribe subscribe = new Subscribe(SubscribeTest.class.getName(), retryConscructor.get(), () -> {
            retryConscructor.getAndDecrement();
            throw new IllegalArgumentException();
        });

        subscribe.subscribe();

        Thread.sleep(500);

        assertEquals(0, retryConscructor.get());
    }
}
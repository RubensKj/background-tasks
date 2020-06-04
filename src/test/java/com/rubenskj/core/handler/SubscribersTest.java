package com.rubenskj.core.handler;

import com.rubenskj.core.interfaces.ICallback;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class SubscribersTest {

    @Test
    public void handle() throws InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        String id = UUID.randomUUID().toString();

        Subscribers subscribers = new Subscribers();

        subscribers.register(
                id,
                Subscribers.class.getName(),
                1,
                this.handleCallbackTest(atomicBoolean),
                1);

        subscribers.handle(id);

        Thread.sleep(100);

        assertTrue(atomicBoolean.get());
    }

    private ICallback handleCallbackTest(AtomicBoolean atomicBoolean) {
        return () -> atomicBoolean.set(true);
    }

    @Test
    public void register() throws NoSuchFieldException, IllegalAccessException {
        String id = UUID.randomUUID().toString();

        Subscribers subscribers = new Subscribers();

        subscribers.register(
                id,
                Subscribers.class.getName(),
                1,
                getHandlerCallback(),
                1);

        Field field = subscribers.getClass().getDeclaredField("SUBSCRIBERS");

        field.setAccessible(true);

        Map<String, Subscriber> subscribersList = (Map<String, Subscriber>) field.get(subscribers);

        Subscriber subscriber = subscribersList.get(id);

        field.setAccessible(false);

        assertNotEquals(null, subscriber);
        assertEquals(
                new Subscriber(Subscribers.class.getName(), 1, getHandlerCallback()).toString(),
                subscriber.toString()
        );
    }

    private ICallback getHandlerCallback() {
        return () -> {
        };
    }
}
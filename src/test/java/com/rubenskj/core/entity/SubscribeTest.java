package com.rubenskj.core.entity;

import com.rubenskj.core.handler.Subscriber;
import com.rubenskj.core.handler.Subscribers;
import com.rubenskj.core.interfaces.ICallback;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
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

    @Test
    void registerAsSubscribeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String id = UUID.randomUUID().toString();
        String subscribeName = SubscribeTest.class.getName();
        int retryTimes = 1;
        ICallback callback = () -> {
        };
        int consumers = 1;

        Subscribe subscribe = new Subscribe(subscribeName, callback);

        Class<?> clazz = subscribe.getClass();

        Method registerAsSubscribe = clazz.getDeclaredMethod("registerAsSubscribe", String.class, String.class, int.class, ICallback.class, int.class);

        registerAsSubscribe.setAccessible(true);

        registerAsSubscribe.invoke(subscribe, id, subscribeName, retryTimes, callback, consumers);

        Map<String, Subscriber> subscribersList = this.getSubscribersList();

        Subscriber subscriber = subscribersList.get(id);

        assertEquals(SubscribeTest.class.getName(), subscriber.getSubscriberName());
        assertEquals(retryTimes, subscriber.getRetry());
        assertEquals(callback, subscriber.getCallback());
    }

    Map<String, Subscriber> getSubscribersList() throws NoSuchFieldException, IllegalAccessException {
        Subscribers subscribers = new Subscribers();

        Class<?> clazz = subscribers.getClass();

        Field field = clazz.getDeclaredField("SUBSCRIBERS");

        field.setAccessible(true);

        Map<String, Subscriber> subscribersList = (Map<String, Subscriber>) field.get(subscribers);

        return subscribersList;
    }
}
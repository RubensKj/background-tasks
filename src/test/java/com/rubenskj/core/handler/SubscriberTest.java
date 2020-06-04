package com.rubenskj.core.handler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubscriberTest {

    @Test
    public void setRetry() {
        int retryWillBeSet = 4;

        Subscriber subscriber = new Subscriber(SubscriberTest.class.getName(), 1, () -> {
        });

        subscriber.setRetry(retryWillBeSet);

        assertEquals(retryWillBeSet, subscriber.getRetry());
    }
}
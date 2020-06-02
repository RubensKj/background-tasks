package com.rubenskj.core.entity;

import com.rubenskj.core.handler.Subscriber;
import com.rubenskj.core.handler.Subscribers;
import com.rubenskj.core.interfaces.ICallback;

import java.util.UUID;

public class Subscribe {

    private static final int DEFAULT_RETRY = 1;

    private final String id;
    private final String subscribeName;
    private final Subscribers subscribers;
    private final Subscriber subscriber;

    public Subscribe(String subscribeName, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        subscribers = new Subscribers();
        subscriber = subscribers.register(id, subscribeName, 0, callback, false);
    }

    public Subscribe(String subscribeName, int retry, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        subscribers = new Subscribers();
        subscriber = subscribers.register(id, subscribeName, retry, callback, true);
    }

    public Subscribe(String subscribeName, int retry, ICallback callback, boolean wantFallback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        subscribers = new Subscribers();
        subscriber = subscribers.register(id, subscribeName, retry, callback, wantFallback);
    }

    public Subscribe(String subscribeName, ICallback callback, boolean wantFallback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        subscribers = new Subscribers();
        subscriber = subscribers.register(id, subscribeName, DEFAULT_RETRY, callback, wantFallback);
    }

    public String getId() {
        return id;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    public void subscribe() {
        subscribers.handle(id, subscriber);
    }
}

package com.rubenskj.core.entity;

import com.rubenskj.core.handler.Subscribers;
import com.rubenskj.core.interfaces.ICallback;

import java.util.UUID;

public class Subscribe {

    private final String id;
    private final String subscribeName;
    private final Subscribers subscribers;

    public Subscribe(String subscribeName, int retry, ICallback callback, boolean wantFallback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        subscribers = new Subscribers();
        subscribers.register(id, subscribeName, retry, callback, wantFallback);
    }

    public String getId() {
        return id;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    public void subscribe() {
        subscribers.handle(id);
    }
}

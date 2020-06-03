package com.rubenskj.core.entity;

import com.rubenskj.core.handler.Subscribers;
import com.rubenskj.core.interfaces.ICallback;

import java.util.UUID;

public class Subscribe {

    private static final int DEFAULT_CONSUMERS = 1;
    private static final int DEFAULT_RETRY = 1;

    private final String id;
    private final String subscribeName;

    public Subscribe(String subscribeName, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, DEFAULT_RETRY, callback, DEFAULT_CONSUMERS);
    }

    public Subscribe(String subscribeName, ICallback callback, int consumers) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, DEFAULT_RETRY, callback, consumers);
    }

    public Subscribe(String subscribeName, int retry, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, retry, callback, DEFAULT_CONSUMERS);
    }

    public Subscribe(String subscribeName, int retry, ICallback callback, int consumers) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, retry, callback, consumers);
    }

    private void registerAsSubscribe(String id, String subscribeName, int retry, ICallback callback, int consumers) {
        new Subscribers().register(id, subscribeName, retry, callback, consumers);
    }

    public String getId() {
        return id;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    public void subscribe() {
        new Subscribers().handle(id);
    }
}

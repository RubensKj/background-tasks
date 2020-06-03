package com.rubenskj.core.interfaces;

import com.rubenskj.core.handler.Subscriber;

public interface ISubscribe {

    void handle(String id);

    void register(String id, String subscriberName, int retry, ICallback callback, boolean wantFallback);
}

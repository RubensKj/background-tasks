package com.rubenskj.core.interfaces;

public interface ISubscribe {

    void handle(String id);

    void register(String id, String subscriberName, int retry, ICallback callback, int consumers);
}

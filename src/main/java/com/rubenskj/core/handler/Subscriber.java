package com.rubenskj.core.handler;

import com.rubenskj.core.interfaces.ICallback;

import java.util.concurrent.atomic.AtomicInteger;

public class Subscriber {

    private AtomicInteger passed = new AtomicInteger(0);

    private final String subscriberName;
    private final ICallback callback;
    private boolean isFinished;
    private int retry;
    private boolean wantFallback;

    public Subscriber(String subscriberName, int retry, ICallback callback, boolean wantFallback) {
        this.subscriberName = subscriberName;
        this.retry = retry;
        this.callback = callback;
        this.wantFallback = wantFallback;
        this.isFinished = false;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public ICallback getCallback() {
        return callback;
    }

    public boolean isWantFallback() {
        return wantFallback;
    }

    public void setWantFallback(boolean wantFallback) {
        this.wantFallback = wantFallback;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public AtomicInteger getPassed() {
        return passed;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "passed=" + passed +
                ", subscriberName='" + subscriberName + '\'' +
                ", callback=" + callback +
                ", isFinished=" + isFinished +
                ", retry=" + retry +
                ", wantFallback=" + wantFallback +
                '}';
    }
}

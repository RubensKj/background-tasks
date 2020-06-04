package com.rubenskj.core.handler;

import com.rubenskj.core.entity.Subscribe;
import com.rubenskj.core.interfaces.ICallback;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * {@link Subscriber} is a class that store the {@link Subscribe} object that was created.
 * This is a representation during the {@link Subscribers} methods execution;
 * <p>
 *
 * @author Rubens K. Junior
 * @see Subscribers
 * @since 0.1
 */
public class Subscriber {

    /**
     * {@link #passed} is the int that is used to count how many times that the callback's thread
     * is executing.
     */
    private final AtomicInteger passed = new AtomicInteger(2);

    private final String subscriberName;
    private final ICallback callback;
    private boolean isFinished;
    private int retry;

    /**
     * <p>
     * Constructor of Subscriber object
     * </p>
     *
     * @param subscriberName is the name for Subscribe that will be shown in the logger of console.
     * @param retry          number of times that will be retried.
     * @param callback       the method will be executed.
     */
    public Subscriber(String subscriberName, int retry, ICallback callback) {
        this.subscriberName = subscriberName;
        this.retry = retry;
        this.callback = callback;
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
                '}';
    }
}

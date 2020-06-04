package com.rubenskj.core.entity;

import com.rubenskj.core.handler.Subscriber;
import com.rubenskj.core.handler.Subscribers;
import com.rubenskj.core.interfaces.ICallback;

import java.util.UUID;

/**
 * <p>
 * {@link Subscribe} is the object that will be used to create the callback
 * that will be executed separately;
 * <p>
 *
 * @author Rubens K. Junior
 * @see Subscriber
 * @see Subscribers
 * @since 0.1
 */
public class Subscribe {

    private static final int DEFAULT_CONSUMERS = 1;
    private static final int DEFAULT_RETRY = 1;

    private final String id;
    private final String subscribeName;

    /**
     * <p>
     * Constructor of subscribe object
     * </p>
     *
     * @param subscribeName is the name for Subscribe that will be shown in the logger of console.
     * @param callback      the method will be executed.
     */
    public Subscribe(String subscribeName, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, DEFAULT_RETRY, callback, DEFAULT_CONSUMERS);
    }

    /**
     * <p>
     * Constructor of subscribe object
     * </p>
     *
     * @param subscribeName is the name for Subscribe that will be shown in the logger of console.
     * @param callback      the method will be executed.
     * @param consumers     number of threads will be created to handle the callback.
     */
    public Subscribe(String subscribeName, ICallback callback, int consumers) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, DEFAULT_RETRY, callback, consumers);
    }

    /**
     * <p>
     * Constructor of subscribe object
     * </p>
     *
     * @param subscribeName is the name for Subscribe that will be shown in the logger of console.
     * @param retry         number of times that will retry the method if throws any exception.
     * @param callback      the method will be executed.
     */
    public Subscribe(String subscribeName, int retry, ICallback callback) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, retry, callback, DEFAULT_CONSUMERS);
    }

    /**
     * <p>
     * Constructor of subscribe object
     * </p>
     *
     * @param subscribeName is the name for Subscribe that will be shown in the logger of console.
     * @param retry         number of times that will retry the method if throws any exception.
     * @param callback      the method will be executed.
     * @param consumers     number of threads will be created to handle the callback.
     */
    public Subscribe(String subscribeName, int retry, ICallback callback, int consumers) {
        this.id = UUID.randomUUID().toString();
        this.subscribeName = subscribeName;
        registerAsSubscribe(id, subscribeName, retry, callback, consumers);
    }

    /**
     * <p>
     * Register method that integrate with Subscribers class. It add as a subscribe to
     * be executed when {@link #subscribe()} is called.
     * </p>
     *
     * @param id            is passed automatically when create a {@link Subscribe} object.
     * @param subscribeName is the name for Subscribe that will be shown in the logger of console.
     * @param retry         number of times that will retry the method if throws any exception.
     * @param callback      the method will be executed.
     * @param consumers     number of threads will be created to handle the callback.
     */
    private void registerAsSubscribe(String id, String subscribeName, int retry, ICallback callback, int consumers) {
        new Subscribers().register(id, subscribeName, retry, callback, consumers);
    }

    public String getId() {
        return id;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    /**
     * <p>
     * Execute the {@link ICallback} that was passed in the constructor.
     * </p>
     * <p>
     * In a more generic way, just call subscribe() to execute {@link ICallback} in another thread.
     */
    public void subscribe() {
        new Subscribers().handle(id);
    }
}

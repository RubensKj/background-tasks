package com.rubenskj.core.interfaces;

import com.rubenskj.core.entity.Subscribe;
import com.rubenskj.core.handler.Subscribers;

/**
 * <p>
 * {@link ISubscribe} is an interface that is used to handle the {@link Subscribe} object
 * that was was created. Also, register the subscribe to {@link Subscribers} class.
 * <p>
 *
 * @author Rubens K. Junior
 * @see Subscribe
 * @see Subscribers
 * @since 0.1
 */
public interface ISubscribe {

    /**
     * <p>
     * When an object {@link Subscribe} object use {@link Subscribe#subscribe()} it will handle
     * with it id.
     * </p>
     * <p>
     * In general <code>handle</code> is the method that will be execute the {@link ICallback}
     *
     * @param id is an uuid that is automatically passed.
     */
    void handle(String id);

    /**
     * <p>
     * When an {@link Subscribe} object is created, is automatically created a register in {@link Subscribers} class
     * to be executed in future.
     * </p>
     * <p>
     *
     * @param id             is an uuid that is automatically passed.
     * @param subscriberName is the name o subscribe that will be shown in the console.
     * @param retry          number of times that will be executed with something went wrong during execution.
     * @param callback       is the method that will be executed.
     * @param consumers      number of threads that will be created for this callback method.
     */
    void register(String id, String subscriberName, int retry, ICallback callback, int consumers);
}

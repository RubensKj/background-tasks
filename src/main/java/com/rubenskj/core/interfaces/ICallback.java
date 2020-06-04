package com.rubenskj.core.interfaces;

import com.rubenskj.core.entity.Subscribe;
import com.rubenskj.core.handler.Subscribers;

/**
 * <p>
 * {@link ICallback} is a {@link FunctionalInterface} that is used to handle the callback
 * that was passed in the conscructor of {@link Subscribe}.
 * <p>
 *
 * @author Rubens K. Junior
 * @see Subscribe
 * @see Subscribers
 * @since 0.1
 */
@FunctionalInterface
public interface ICallback {
    /**
     * When an object add as attribute <code>ICallback</code>, it'll be used
     * to create a separated method, that when the <code>handle</code> method is called
     * is executed in separately thread.
     * <p>
     * <p>
     * In general <code>handle</code> is the method that will be executed
     *
     * @see Subscribers createTask method
     */
    void handle() throws Exception;
}

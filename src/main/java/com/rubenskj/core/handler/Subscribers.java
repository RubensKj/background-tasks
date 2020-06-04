package com.rubenskj.core.handler;

import com.rubenskj.core.entity.Subscribe;
import com.rubenskj.core.interfaces.ICallback;
import com.rubenskj.core.interfaces.ISubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.rubenskj.core.util.ValidationUtils.validateString;

/**
 * <p>
 * {@link Subscribers} is the implementation of {@link ISubscribe},
 *
 * <p>
 * Subscribers make all the process of running a subscribe in a second thread.
 *
 * @author Rubens K. Junior
 * @see Subscribe
 * @see Subscriber
 * @since 0.1
 */
public class Subscribers implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscribers.class);

    private static final Map<String, Subscriber> SUBSCRIBERS = new ConcurrentHashMap<>();
    private static final Map<String, ExecutorService> EXECUTORS = new HashMap<>();

    /**
     * <p>
     * Handler method that execute the {@link Subscribe#subscribe()} method.
     * <p>
     *
     * @param id the uuid generated during the creation of the {@link Subscribe}
     */
    @Override
    public void handle(String id) {
        validateString(id, "ID from subscriber cannot be null or empty");

        Subscriber subscriber = SUBSCRIBERS.get(id);

        Runnable task = createTask(id, subscriber);

        ExecutorService executorService = EXECUTORS.get(id);
        executorService.submit(task);
    }

    /**
     * <p>
     * Create task to be executed in another thread in {@link Subscribers#handle}.
     * <p>
     *
     * @param id         uuid that is logged in the console.
     * @param subscriber that will be executed the callback.
     * @return A {@link Runnable} task to be executed.
     */
    private Runnable createTask(String id, Subscriber subscriber) {
        if (subscriber.isFinished()) {
            throw new IllegalArgumentException("Cannot exeucte subscribe that is already finished. ID -> " + id);
        }

        return () -> {
            try {
                MDC.put("subscriberListId", id);
                MDC.put("subscriberName", subscriber.getSubscriberName());

                subscriber.getCallback().handle();

                finishSubscriber(id, subscriber);
            } catch (Exception e) {
                LOGGER.error("Error on processing subscriber -> ", e);

                int numberPassed = subscriber.getPassed().get();

                int retry = subscriber.getRetry();
                if (numberPassed <= retry) {
                    LOGGER.info("Putting callback in fallback");

                    subscriber.getPassed().getAndIncrement();

                    handle(id);
                } else {
                    finishSubscriber(id, subscriber);
                }
            } finally {
                MDC.remove("subscriberListId");
                MDC.remove("subscriberName");
            }
        };
    }

    /**
     * <p>
     * Method is to finish the subscriber and remove from the list.
     * <p>
     *
     * @param id         uuid that will be removed from {@link #SUBSCRIBERS} list.
     * @param subscriber ({@link Subscriber}) to set is finished.
     */
    private void finishSubscriber(String id, Subscriber subscriber) {
        subscriber.setFinished(true);
        SUBSCRIBERS.remove(id);
    }

    /**
     * <p>
     * Register method that add the subscribe in {@link Subscribers#SUBSCRIBERS}, and create
     * the Executors to be executed when {@link Subscribe#subscribe()} is called.
     * <p>
     *
     * @param id             is passed automatically when create a {@link Subscribe} object.
     * @param subscriberName is the name for Subscribe that will be shown in the logger of console.
     * @param retry          number of times that will retry the method if throws any exception.
     * @param callback       the method will be executed.
     * @param consumers      number of threads will be created to handle the callback.
     */
    @Override
    public void register(String id, String subscriberName, int retry, ICallback callback, int consumers) {
        validateString(id, "ID from subscriber cannot be null or empty");
        validateString(subscriberName, "SubscriberName cannot be null or empty");

        Subscriber subscriber = new Subscriber(subscriberName, retry, callback);
        SUBSCRIBERS.put(id, subscriber);
        EXECUTORS.put(id, Executors.newFixedThreadPool(consumers));
    }
}

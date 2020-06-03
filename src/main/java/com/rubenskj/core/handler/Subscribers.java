package com.rubenskj.core.handler;

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

public class Subscribers implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscribers.class);

    private static final Map<String, Subscriber> SUBSCRIBERS = new ConcurrentHashMap<>();
    private static final Map<String, ExecutorService> EXECUTORS = new HashMap<>();

    @Override
    public void handle(String id) {
        validateString(id, "ID from subscriber cannot be null or empty");

        Subscriber subscriber = SUBSCRIBERS.get(id);

        Runnable task = createTask(id, subscriber);

        ExecutorService executorService = EXECUTORS.get(id);
        executorService.submit(task);
    }

    @Override
    public void register(String id, String subscriberName, int retry, ICallback callback, int consumers) {
        validateString(id, "ID from subscriber cannot be null or empty");
        validateString(subscriberName, "SubscriberName cannot be null or empty");

        Subscriber subscriber = new Subscriber(subscriberName, retry, callback);
        SUBSCRIBERS.put(id, subscriber);
        EXECUTORS.put(id, Executors.newFixedThreadPool(consumers));
    }

    private Runnable createTask(String id, Subscriber subscriber) {
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

    private void finishSubscriber(String id, Subscriber subscriber) {
        subscriber.setFinished(true);
        SUBSCRIBERS.remove(id);
    }
}

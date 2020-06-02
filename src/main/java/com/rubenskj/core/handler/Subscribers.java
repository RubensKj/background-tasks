package com.rubenskj.core.handler;

import com.rubenskj.core.interfaces.ICallback;
import com.rubenskj.core.interfaces.ISubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.rubenskj.core.util.ValidationUtils.validateString;

public class Subscribers implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscribers.class);

    public Map<String, Subscriber> SUBSCRIBERS = new ConcurrentHashMap<>();
    public Map<String, Runnable> TASKS = new HashMap<>();
    public Map<String, ExecutorService> EXECUTORS = new HashMap<>();

    private static String uuid = "";

    @Override
    public void handle(String id, Subscriber subscriber) {
        validateString(id, "ID from subscriber cannot be null or empty");

        final Runnable task = TASKS.get(id);

        ExecutorService executorService = EXECUTORS.get(id);
        executorService.submit(task);
    }

    @Override
    public Subscriber register(String id, String subscriberName, int retry, ICallback callback, boolean wantFallback) {
        validateString(id, "ID from subscriber cannot be null or empty");
        validateString(subscriberName, "SubscriberName cannot be null or empty");

        Subscriber subscriber = new Subscriber(subscriberName, retry, callback, wantFallback);
        SUBSCRIBERS.put(id, subscriber);
        EXECUTORS.put(id, Executors.newFixedThreadPool(2));
        TASKS.put(id, createTask(id, subscriber));

        return subscriber;
    }

    private Runnable createTask(String id, Subscriber subscriber) {
        return () -> {
            uuid = UUID.randomUUID().toString();
            LOGGER.info("UUID -> {}", uuid);
            try {

                MDC.put("subscriberListId", id);
                MDC.put("subscriberName", subscriber.getSubscriberName());

                subscriber.getCallback().handle();

                finishSubscriber(id, subscriber);
            } catch (Exception e) {
                LOGGER.error("Error on processing subscriber -> ", e);

                int numberPassed = subscriber.getPassed();

                int retry = subscriber.getRetry();
                if (subscriber.isWantFallback() && numberPassed <= retry) {
                    LOGGER.info("Putting callback in fallback");

                    subscriber.setPassed((numberPassed++));
                    SUBSCRIBERS.replace(id, subscriber);

                    handle(id, subscriber);
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

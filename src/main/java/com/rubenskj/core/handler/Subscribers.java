package com.rubenskj.core.handler;

import com.rubenskj.core.interfaces.ICallback;
import com.rubenskj.core.interfaces.ISubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.rubenskj.core.util.ValidationUtils.validateString;

public class Subscribers implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscribers.class);

    public static final Map<String, Subscriber> SUBSCRIBERS = new ConcurrentHashMap<>();

    @Override
    public void handle(String id) {
        validateString(id, "ID from subscriber cannot be null or empty");

        Subscriber subscriber = SUBSCRIBERS.get(id);

        final Runnable task = this.createTask(id, subscriber);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(task);
    }

    private Runnable createTask(String id, Subscriber subscriber) {
        return () -> {
            try {
                MDC.put("subscriberListId", id);
                MDC.put("subscriberName", subscriber.getSubscriberName());
                subscriber.getCallback().handle();

                subscriber.setFinished(true);
                SUBSCRIBERS.remove(id);
            } catch (Exception e) {
                LOGGER.error("Error on processing subscriber -> ", e);

                if (subscriber.isWantFallback()) {
                    LOGGER.info("Putting callback in fallback");
                    handle(id);
                }
            } finally {
                MDC.remove("subscriberListId");
                MDC.remove("subscriberName");
            }
        };
    }

    @Override
    public void register(String id, String subscriberName, int retry, ICallback callback, boolean wantFallback) {
        validateString(id, "ID from subscriber cannot be null or empty");
        validateString(subscriberName, "SubscriberName cannot be null or empty");

        SUBSCRIBERS.put(id, new Subscriber(subscriberName, retry, callback, wantFallback));
    }
}

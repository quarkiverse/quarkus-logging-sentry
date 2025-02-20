package io.quarkus.logging.sentry;

import java.util.function.BiFunction;

import jakarta.enterprise.inject.Instance;

import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions.BeforeSendCallback;

/**
 * Executes beans marked with BeforeSend callback interface.
 */
public class SentryBeforeSendCallbacksHandler implements BiFunction<SentryEvent, Hint, SentryEvent> {
    private final Instance<BeforeSendCallback> callbacks;

    public SentryBeforeSendCallbacksHandler(Instance<BeforeSendCallback> callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public SentryEvent apply(SentryEvent sentryEvent, Hint hint) {
        if (sentryEvent != null) {
            for (BeforeSendCallback callback : callbacks) {
                assert sentryEvent != null;
                sentryEvent = callback.execute(sentryEvent, hint);
            }
        }
        return sentryEvent;
    }
}
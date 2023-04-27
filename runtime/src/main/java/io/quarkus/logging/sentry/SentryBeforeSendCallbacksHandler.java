package io.quarkus.logging.sentry;

import jakarta.enterprise.inject.spi.CDI;

import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions.BeforeSendCallback;

/**
 * Executes beans marked with BeforeSend callback interface.
 */
public class SentryBeforeSendCallbacksHandler {
    public SentryEvent executeCallbacks(SentryEvent sentryEvent, Hint hint) {
        if (sentryEvent != null) {
            for (BeforeSendCallback callback : CDI.current().select(BeforeSendCallback.class)) {
                sentryEvent = callback.execute(sentryEvent, hint);
            }
        }
        return sentryEvent;
    }
}

package io.quarkus.logging.sentry;

import jakarta.enterprise.inject.spi.CDI;

import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions.BeforeSendCallback;

/**
 * Executes beans marked with BeforeSend callback interface.
 */
public class SentryBeforeSendCallbacksHandler {
    public void executeCallbacks(SentryEvent sentryEvent, Hint hint) {
        CDI.current().select(BeforeSendCallback.class)
                .forEach(beforeSendCallback -> beforeSendCallback.execute(sentryEvent, hint));
    }
}

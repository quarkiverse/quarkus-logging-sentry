package io.quarkiverse.loggingsentry.it;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.arc.Unremovable;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;

@ApplicationScoped
@Unremovable
public class SentryCallbackHandler implements SentryOptions.BeforeSendCallback {
    private Boolean wasCalled = false;

    @Override
    public SentryEvent execute(SentryEvent sentryEvent, Hint hint) {
        if (sentryEvent.getExceptions().get(0).getType().equals(RuntimeException.class.getSimpleName())) {
            wasCalled = true;
        }
        return null;
    }

    public Boolean wasCalledWithRuntimeException() {
        return wasCalled;
    }
}

package io.quarkiverse.loggingsentry.it;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.arc.Unremovable;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;

@ApplicationScoped
@Unremovable
public class SentryCallbackHandler implements SentryOptions.BeforeSendCallback {
    private Boolean wasCalled = false;
    private Map<String, String> tags;

    @Override
    public SentryEvent execute(SentryEvent sentryEvent, Hint hint) {
        if (sentryEvent.getExceptions().get(0).getType().equals(RuntimeException.class.getSimpleName())) {
            wasCalled = true;
            tags = sentryEvent.getTags();
        }
        return null;
    }

    public Boolean wasCalledWithRuntimeException() {
        return wasCalled;
    }

    public Map<String, String> getTagsOfCall() {
        return tags;
    }

    public void reset() {
        wasCalled = false;
        tags = null;
    }
}

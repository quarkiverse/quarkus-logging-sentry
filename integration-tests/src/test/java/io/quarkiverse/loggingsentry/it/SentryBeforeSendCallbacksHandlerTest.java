package io.quarkiverse.loggingsentry.it;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;

import org.junit.jupiter.api.Test;

import io.quarkus.logging.sentry.SentryBeforeSendCallbacksHandler;
import io.quarkus.test.junit.QuarkusTest;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import io.sentry.protocol.SentryException;

@QuarkusTest
public class SentryBeforeSendCallbacksHandlerTest {

    @Test
    public void testBeforeSendCallbackCanSetEventToNull() {
        final Instance<SentryOptions.BeforeSendCallback> callbacks = CDI.current()
                .select(SentryOptions.BeforeSendCallback.class);
        SentryBeforeSendCallbacksHandler sut = new SentryBeforeSendCallbacksHandler(callbacks);
        SentryEvent testEvent = new SentryEvent();
        SentryException testException = new SentryException();
        testException.setType("Foo");
        testEvent.setExceptions(
                List.of(testException));

        assertThat(
                sut.apply(testEvent, new Hint())).isNull();
    }

    @Test
    public void testBeforeSendCallbackCanBeCalledWithNull() {
        // given
        final Instance<SentryOptions.BeforeSendCallback> callbacks = CDI.current()
                .select(SentryOptions.BeforeSendCallback.class);
        SentryBeforeSendCallbacksHandler sut = new SentryBeforeSendCallbacksHandler(callbacks);

        assertThat(
                sut.apply(null, new Hint())).isNull();
    }
}

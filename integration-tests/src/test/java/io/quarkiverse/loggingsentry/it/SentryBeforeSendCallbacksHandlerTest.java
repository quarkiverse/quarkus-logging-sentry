package io.quarkiverse.loggingsentry.it;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.logging.sentry.SentryBeforeSendCallbacksHandler;
import io.quarkus.test.junit.QuarkusTest;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.protocol.SentryException;

@QuarkusTest
public class SentryBeforeSendCallbacksHandlerTest {

    @Test
    public void testBeforeSendCallbackCanSetEventToNull() {
        SentryBeforeSendCallbacksHandler sut = new SentryBeforeSendCallbacksHandler();
        SentryEvent testEvent = new SentryEvent();
        SentryException testException = new SentryException();
        testException.setType("Foo");
        testEvent.setExceptions(
                List.of(testException));

        assertThat(
                sut.executeCallbacks(testEvent, new Hint())).isNull();
    }

    @Test
    public void testBeforeSendCallbackCanBeCalledWithNull() {
        // given
        SentryBeforeSendCallbacksHandler sut = new SentryBeforeSendCallbacksHandler();

        assertThat(
                sut.executeCallbacks(null, new Hint())).isNull();
    }
}

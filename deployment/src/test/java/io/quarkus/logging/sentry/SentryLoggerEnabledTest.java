package io.quarkus.logging.sentry;

import static io.quarkus.logging.sentry.SentryLoggerTest.getSentryHandler;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.sentry.Sentry;

public class SentryLoggerEnabledTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setAllowTestClassOutsideDeployment(true)
            .withConfigurationResource("application-sentry-logger-enabled.properties");

    @Test
    public void sentryLoggerEnabledTest() {
        //test enabling sentry using quarkus.log.sentry.enabled
        final Handler sentryHandler = getSentryHandler();
        assertThat(sentryHandler).isNotNull();
        assertThat(Sentry.isEnabled()).isTrue();
    }

}

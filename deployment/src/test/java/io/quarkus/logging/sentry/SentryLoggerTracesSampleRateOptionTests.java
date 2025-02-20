package io.quarkus.logging.sentry;

import static io.quarkus.logging.sentry.SentryLoggerTest.getSentryHandler;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.sentry.ScopesAdapter;
import io.sentry.SentryOptions;

public class SentryLoggerTracesSampleRateOptionTests {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setAllowTestClassOutsideDeployment(true)
            .withConfigurationResource("application-sentry-logger-traces-sample-rate.properties");

    @Test
    public void sentryLoggerEnvironmentOptionTest() {
        final Handler sentryHandler = getSentryHandler();
        final SentryOptions options = ScopesAdapter.getInstance().getOptions();
        assertThat(sentryHandler).isNotNull();
        assertThat(options.getTracesSampleRate()).isOne();
    }

}

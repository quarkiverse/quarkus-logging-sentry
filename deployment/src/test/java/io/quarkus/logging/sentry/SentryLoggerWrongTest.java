package io.quarkus.logging.sentry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.runtime.configuration.ConfigurationException;
import io.quarkus.test.QuarkusExtensionTest;

public class SentryLoggerWrongTest {

    @RegisterExtension
    static final QuarkusExtensionTest config = new QuarkusExtensionTest()
            .setAllowTestClassOutsideDeployment(true)
            .withConfigurationResource("application-sentry-logger-wrong.properties")
            .setExpectedException(ConfigurationException.class);

    @Test
    public void sentryLoggerWrongTest() {
        //Exception is expected
    }

}

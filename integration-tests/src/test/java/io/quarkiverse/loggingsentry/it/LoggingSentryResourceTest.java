package io.quarkiverse.loggingsentry.it;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoggingSentryResourceTest {
    @Inject
    SentryCallbackHandler sentryCallbackHandler;

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/logging-sentry")
                .then()
                .statusCode(200)
                .body(is("Hello logging-sentry"));
    }

    @Test
    public void testBeforeSendCallback() {
        given()
                .when().get("/logging-sentry/broken")
                .then()
                .statusCode(500);

        assertThat(sentryCallbackHandler.wasCalledWithRuntimeException()).isTrue();
    }
}

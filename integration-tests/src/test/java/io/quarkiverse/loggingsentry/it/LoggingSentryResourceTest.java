package io.quarkiverse.loggingsentry.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoggingSentryResourceTest {
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
        assertCallbackHandlerCallStatus(false);

        given()
                .when().get("/logging-sentry/broken")
                .then()
                .statusCode(500);

        assertCallbackHandlerCallStatus(true);
    }

    private static void assertCallbackHandlerCallStatus(Boolean wasCalled) {
        given()
                .when().get("/logging-sentry/get-callback-handler-status")
                .then()
                .statusCode(200)
                .body(is(wasCalled.toString()));
    }
}

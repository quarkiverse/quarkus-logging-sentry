package io.quarkiverse.loggingsentry.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LoggingSentryResourceTest {

    @BeforeEach
    void resetCallbackHandler() {
        given()
                .when().post("/logging-sentry/reset-callback-handler/reset")
                .then()
                .statusCode(204);
    }

    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/logging-sentry")
                .then()
                .statusCode(200)
                .body(is("Hello logging-sentry"));
    }

    @Test
    void testBeforeSendCallback_shouldBeCalled() {
        assertCallbackHandlerCallStatus(false);

        given()
                .when().get("/logging-sentry/broken")
                .then()
                .statusCode(500);

        assertCallbackHandlerCallStatus(true);
    }

    @Test
    void testBeforeSendCallback_shouldBeCalledWithExpectedTags() {
        assertCallbackHandlerCallStatus(false);

        given()
                .when().get("/logging-sentry/broken")
                .then()
                .statusCode(500);

        assertCallbackHandlerTags(Map.of("test.tag", "testvalue"));
    }

    private static void assertCallbackHandlerCallStatus(Boolean wasCalled) {
        given()
                .when().get("/logging-sentry/get-callback-handler-status")
                .then()
                .statusCode(200)
                .body(is(wasCalled.toString()));
    }

    private static void assertCallbackHandlerTags(Map<String, String> tags) {
        given()
                .when().get("/logging-sentry/get-callback-handler-tags")
                .then()
                .statusCode(200)
                .body(is(tags.toString()));
    }
}

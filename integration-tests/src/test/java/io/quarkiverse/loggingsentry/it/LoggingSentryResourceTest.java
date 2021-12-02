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
}

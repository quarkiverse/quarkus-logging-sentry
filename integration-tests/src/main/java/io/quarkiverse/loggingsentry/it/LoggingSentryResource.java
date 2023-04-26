package io.quarkiverse.loggingsentry.it;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/logging-sentry")
@ApplicationScoped
public class LoggingSentryResource {

    @GET
    public String hello() {
        return "Hello logging-sentry";
    }

    @GET
    @Path("/broken")
    public String nope() {
        throw new RuntimeException("broken");
    }
}

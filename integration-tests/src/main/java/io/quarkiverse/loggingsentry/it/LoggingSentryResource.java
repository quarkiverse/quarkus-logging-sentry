package io.quarkiverse.loggingsentry.it;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/logging-sentry")
@ApplicationScoped
public class LoggingSentryResource {

    @GET
    public String hello() {
        return "Hello logging-sentry";
    }
}

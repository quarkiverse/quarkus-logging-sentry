package io.quarkiverse.loggingsentry.it;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/logging-sentry")
@ApplicationScoped
public class LoggingSentryResource {
    @Inject
    SentryCallbackHandler sentryCallbackHandler;

    @GET
    public String hello() {
        return "Hello logging-sentry";
    }

    @GET
    @Path("/broken")
    public String nope() {
        throw new RuntimeException("broken");
    }

    @GET
    @Path("/get-callback-handler-status")
    public String getCallbackHandlerStatus() {
        return sentryCallbackHandler.wasCalledWithRuntimeException().toString();
    }

    @GET
    @Path("/get-callback-handler-tags")
    public String getCallbackHandlerTags() {
        return sentryCallbackHandler.getTagsOfCall().toString();
    }

    @POST
    @Path("/reset-callback-handler/reset")
    public void resetCallbackHandler() {
        sentryCallbackHandler.reset();
    }

}

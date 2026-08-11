package io.quarkus.logging.sentry;

import io.quarkus.logging.Log;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import io.sentry.ITransaction;
import io.sentry.Sentry;
import io.vertx.core.http.HttpServerRequest;

@SentryHttp()
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class SentryInterceptor {

    @Inject
    HttpServerRequest request;

    @AroundInvoke
    Object intercept(InvocationContext ctx) throws Exception {
        ITransaction iTransaction = Sentry.startTransaction(request.path(), request.path());
        Object proceed = ctx.proceed();
        Log.debug("Intercepted "+request.path()+" "+request.path());
        iTransaction.finish();
        return proceed;
    }
}

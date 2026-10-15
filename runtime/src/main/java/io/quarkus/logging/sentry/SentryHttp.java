package io.quarkus.logging.sentry;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RUNTIME)
@Target({ ElementType.TYPE })
public @interface SentryHttp {
}

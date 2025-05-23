package io.quarkus.logging.sentry;

import static java.util.function.Predicate.not;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Handler;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;

import org.jboss.logging.Logger;

import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import io.quarkus.runtime.configuration.ConfigurationException;
import io.sentry.SentryOptions;
import io.sentry.jul.SentryHandler;

@Recorder
public class SentryHandlerValueFactory {
    private static final Logger LOG = Logger.getLogger(SentryHandlerValueFactory.class);

    public RuntimeValue<Optional<Handler>> create(final SentryConfig config) {

        if (!config.enabled()) {
            return new RuntimeValue<>(Optional.empty());
        }

        // Init Sentry
        final SentryOptions options = toSentryOptions(config);
        final boolean enableExternalConfiguration = false; // https://github.com/getsentry/sentry-java/pull/4208
        SentryHandler handler = new SentryHandler(options, enableExternalConfiguration);
        handler.setLevel(config.level());
        handler.setPrintfStyle(true);
        try {
            handler.setEncoding(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            LOG.warn("Could not set encoding for Sentry handler", e);
        }
        handler.setMinimumEventLevel(config.minimumEventLevel() != null ? config.minimumEventLevel() : config.level());
        handler.setMinimumBreadcrumbLevel(config.minimumBreadcrumbLevel());
        LOG.infof("Sentry connected to: %s", config.dsn().orElse("?"));
        return new RuntimeValue<>(Optional.of(handler));
    }

    public static SentryOptions toSentryOptions(SentryConfig sentryConfig) {
        if (sentryConfig.dsn().isEmpty()) {
            throw new ConfigurationException(
                    "Configuration key \"quarkus.log.sentry.dsn\" is required when Sentry is enabled, but its value is empty/missing");
        }
        final SentryOptions options = new SentryOptions();

        if (sentryConfig.inAppPackages().isEmpty()) {
            LOG.warn(
                    "No 'quarkus.log.sentry.in-app-packages' was configured, this option is highly recommended as it affects stacktrace grouping and display on Sentry. See https://quarkus.io/guides/logging-sentry#in-app-packages");
        } else {
            List<String> inAppPackages = sentryConfig.inAppPackages().get();
            if (inAppPackages.size() != 1 || !Objects.equals(inAppPackages.get(0), "*")) {
                inAppPackages.forEach(options::addInAppInclude);
            }
        }

        if (sentryConfig.inAppExcludedPackages().isPresent()) {
            List<String> inAppExcludedPackages = sentryConfig.inAppExcludedPackages().get();
            inAppExcludedPackages.forEach(options::addInAppExclude);
        }

        if (sentryConfig.ignoredExceptionsForType().isPresent()) {
            List<String> ignoredExceptionsForType = sentryConfig.ignoredExceptionsForType().get();
            ignoredExceptionsForType.forEach(exceptionTypeName -> {
                try {
                    Class<? extends Throwable> exceptionClass = Class.forName(exceptionTypeName).asSubclass(Throwable.class);
                    options.addIgnoredExceptionForType(exceptionClass);
                } catch (ClassNotFoundException e) {
                    LOG.error(String.format(
                            "Exception type not found for the given name: %s, in 'quarkus.log.sentry.ignored-exceptions-for-type'",
                            exceptionTypeName), e);
                }
            });
        }

        options.setDsn(sentryConfig.dsn().get());
        sentryConfig.environment().ifPresent(options::setEnvironment);
        sentryConfig.release().ifPresent(options::setRelease);
        sentryConfig.ignoredErrors().ifPresent(options::setIgnoredErrors);
        sentryConfig.serverName().ifPresent(options::setServerName);
        sentryConfig.tracesSampleRate().ifPresent(options::setTracesSampleRate);
        sentryConfig.contextTags().ifPresent(contextTags -> contextTags.forEach(options::addContextTag));
        sentryConfig.tags().forEach(options::setTag);

        final Instance<SentryOptions.BeforeSendCallback> select = CDI.current().select(SentryOptions.BeforeSendCallback.class);
        if (!select.isUnsatisfied()) {
            final SentryBeforeSendCallbacksHandler handler = new SentryBeforeSendCallbacksHandler(select);
            options.setBeforeSend(handler::apply);
        }

        if (sentryConfig.proxy().enabled()) {
            if (sentryConfig.proxy().host().filter(not(String::isBlank)).isPresent()) {
                LOG.trace("Proxy is enabled for Sentry's outgoing requests");
                options.setProxy(new SentryOptions.Proxy(
                        sentryConfig.proxy().host().orElseThrow(),
                        sentryConfig.proxy().port().map(String::valueOf).orElse(null),
                        sentryConfig.proxy().username().orElse(null),
                        sentryConfig.proxy().password().orElse(null)));
            } else {
                LOG.warn("Proxy is enabled for Sentry but no host is provided. Ignoring Proxy configuration.");
            }
        }

        options.setDebug(sentryConfig.debug());
        return options;
    }
}
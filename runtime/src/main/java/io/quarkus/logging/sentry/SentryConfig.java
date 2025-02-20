package io.quarkus.logging.sentry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.logging.Level;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithParentName;

/**
 * Configuration for Sentry logging.
 */
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
@ConfigMapping(prefix = "quarkus.log.sentry")
public interface SentryConfig {

    /**
     * Determine whether to enable the Sentry logging extension.
     *
     * @deprecated we try to stay away from this pattern now, replace with {@code quarkus.log.sentry.enabled}.
     */
    @Deprecated(forRemoval = true)
    @WithParentName
    Optional<Boolean> enable();

    /**
     * Determine whether to enable the Sentry logging extension.
     */
    @WithDefault("false")
    boolean enabled();

    /**
     * Sentry DSN
     * <p>
     * The DSN is the first and most important thing to configure because it tells the SDK where to send events. You can find
     * your project’s DSN in the “Client Keys” section of your “Project Settings” in Sentry.
     */
    Optional<String> dsn();

    /**
     * The sentry log level.
     */
    @WithDefault("WARN")
    Level level();

    /**
     * The minimum event level.
     * <p>
     * Every log statement that is greater than minimum event level is turned into Sentry event.
     */
    @WithDefault("WARN")
    Level minimumEventLevel();

    /**
     * The minimum breadcrumb level.
     * <p>
     * Every log statement that is greater than minimum breadcrumb level is added to Sentry scope as a breadcrumb,
     * which can be later attached to SentryEvent if one is triggered.
     */
    @WithDefault("INFO")
    Level minimumBreadcrumbLevel();

    /**
     * Sentry differentiates stack frames that are directly related to your application (“in application”) from stack frames
     * that come from other packages such as the standard library, frameworks, or other dependencies. The difference is visible
     * in the Sentry web interface where only the “in application” frames are displayed by default.
     * <p>
     * You can configure which package prefixes your application uses with this option.
     * <p>
     * This option is highly recommended as it affects stacktrace grouping and display on Sentry. See documentation:
     * <a href="https://quarkus.io/guides/logging-sentry#in-app-packages">...</a>
     */
    Optional<List<String>> inAppPackages();

    /**
     * Sentry differentiates stack frames that are directly related to your application (“in application”) from stack frames
     * that come from other packages such as the standard library, frameworks, or other dependencies. The difference is visible
     * in the Sentry web interface where only the “in application” frames are displayed by default.
     * <p>
     * You can configure which package prefixes your application uses with this option.
     * <p>
     * You can configure which package prefixes you want to exclude from logging.
     */
    Optional<List<String>> inAppExcludedPackages();

    /**
     *
     * You can use this option to set exceptions that will be filtered out before sending to Sentry
     * by adding the names of the exception.(e.g. java.lang.RuntimeException)
     *
     */
    Optional<List<String>> ignoredExceptionsForType();

    /**
     *
     * You can use this option to filter out errors whose message matches a certain pattern before sending to Sentry.
     *
     */
    Optional<List<String>> ignoredErrors();

    /**
     * Environment
     * <p>
     * With Sentry, you can easily filter issues, releases, and user feedback by environment.
     * The environment filter on sentry affects all issue-related metrics like count of users affected, times series graphs,
     * and event count.
     * By setting the environment option, an environment tag will be added to each new issue sent to Sentry.
     * <p>
     * There are a few restrictions:
     * -> the environment name cannot contain newlines or spaces, cannot be the string “None” or exceed 64 characters.
     *
     */
    Optional<String> environment();

    /**
     * Release
     * <p>
     * A release is a version of your code that is deployed to an environment.
     * When you give Sentry information about your releases, you unlock a number of new features:
     * - Determine the issues and regressions introduced in a new release
     * - Predict which commit caused an issue and who is likely responsible
     * - Resolve issues by including the issue number in your commit message
     * - Receive email notifications when your code gets deployed
     *
     */
    Optional<String> release();

    /**
     * Server name
     * <p>
     * Sets the server name that will be sent with each event.
     */
    Optional<String> serverName();

    /**
     * Debug
     * <p>
     * Enables Sentry debug mode.
     */
    @WithDefault("false")
    boolean debug();

    /**
     * This should be a float/double between 0.0 and 1.0 (inclusive) and represents the percentage chance that any given
     * transaction will be sent to Sentry.
     * So, barring outside influence, 0.0 is a 0% chance (none will be sent) and 1.0 is a 100% chance (all will be sent). This
     * rate applies equally to all transactions. Can be anything between 0.0 and 1.0 or null (default), to disable it.
     */
    OptionalDouble tracesSampleRate();

    /**
     * Context Tags
     * <p>
     * Specifics the MDC tags that are used as Sentry tags
     */
    Optional<List<String>> contextTags();

    /**
     * Static tags
     * <p>
     * Static tags that are sent to Sentry with every event.
     */
    Map<String, String> tags();

    SentryProxyConfig proxy();
}
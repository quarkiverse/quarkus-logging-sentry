package io.quarkus.logging.sentry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.logging.Level;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * Configuration for Sentry logging.
 */
@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = "log.sentry")
public class SentryConfig {

    /**
     * Determine whether to enable the Sentry logging extension.
     */
    @ConfigItem(name = ConfigItem.PARENT)
    boolean enable;

    /**
     * Sentry DSN
     *
     * The DSN is the first and most important thing to configure because it tells the SDK where to send events. You can find
     * your project’s DSN in the “Client Keys” section of your “Project Settings” in Sentry.
     */
    @ConfigItem
    public Optional<String> dsn;

    /**
     * The sentry log level.
     */
    @ConfigItem(defaultValue = "WARN")
    public Level level;

    /**
     * The minimum event level.
     *
     * Every log statement that is greater than minimum event level is turned into Sentry event.
     */
    @ConfigItem(defaultValue = "WARN")
    public Level minimumEventLevel;

    /**
     * The minimum breadcrumb level.
     *
     * Every log statement that is greater than minimum breadcrumb level is added to Sentry scope as a breadcrumb,
     * which can be later attached to SentryEvent if one is triggered.
     */
    @ConfigItem(defaultValue = "INFO")
    public Level minimumBreadcrumbLevel;

    /**
     * Sentry differentiates stack frames that are directly related to your application (“in application”) from stack frames
     * that come from other packages such as the standard library, frameworks, or other dependencies. The difference is visible
     * in the Sentry web interface where only the “in application” frames are displayed by default.
     *
     * You can configure which package prefixes your application uses with this option.
     *
     * This option is highly recommended as it affects stacktrace grouping and display on Sentry. See documentation:
     * https://quarkus.io/guides/logging-sentry#in-app-packages
     */
    @ConfigItem
    public Optional<List<String>> inAppPackages;

    /**
     * Sentry differentiates stack frames that are directly related to your application (“in application”) from stack frames
     * that come from other packages such as the standard library, frameworks, or other dependencies. The difference is visible
     * in the Sentry web interface where only the “in application” frames are displayed by default.
     *
     * You can configure which package prefixes your application uses with this option.
     *
     * You can configure which package prefixes you want to exclude from logging.
     */
    @ConfigItem
    public Optional<List<String>> inAppExcludedPackages;

    /**
     *
     * You can use this option to set exceptions that will be filtered out before sending to Sentry
     * by adding the names of the exception.(e.g. java.lang.RuntimeException)
     *
     */
    @ConfigItem
    public Optional<List<String>> ignoredExceptionsForType;

    /**
     *
     * You can use this option to filter out errors whose message matches a certain pattern before sending to Sentry.
     *
     */

    @ConfigItem
    public Optional<List<String>> ignoredErrors;

    /**
     * Environment
     *
     * With Sentry you can easily filter issues, releases, and user feedback by environment.
     * The environment filter on sentry affects all issue-related metrics like count of users affected, times series graphs,
     * and event count.
     * By setting the environment option, an environment tag will be added to each new issue sent to Sentry.
     *
     * There are a few restrictions:
     * -> the environment name cannot contain newlines or spaces, cannot be the string “None” or exceed 64 characters.
     *
     */
    @ConfigItem
    public Optional<String> environment;

    /**
     * Release
     *
     * A release is a version of your code that is deployed to an environment.
     * When you give Sentry information about your releases, you unlock a number of new features:
     * - Determine the issues and regressions introduced in a new release
     * - Predict which commit caused an issue and who is likely responsible
     * - Resolve issues by including the issue number in your commit message
     * - Receive email notifications when your code gets deployed
     *
     */
    @ConfigItem
    public Optional<String> release;

    /**
     * Server name
     *
     * Sets the server name that will be sent with each event.
     */
    @ConfigItem
    public Optional<String> serverName;

    /**
     * Debug
     *
     * Enables Sentry debug mode.
     */
    @ConfigItem(defaultValue = "false")
    public boolean debug;

    /**
     * This should be a float/double between 0.0 and 1.0 (inclusive) and represents the percentage chance that any given
     * transaction will be sent to Sentry.
     * So, barring outside influence, 0.0 is a 0% chance (none will be sent) and 1.0 is a 100% chance (all will be sent). This
     * rate applies equally to all transactions.
     */
    @ConfigItem()
    public OptionalDouble tracesSampleRate;

    /**
     * Context Tags
     *
     * Specifics the MDC tags that are used as Sentry tags
     */
    @ConfigItem
    public Optional<List<String>> contextTags;

    /**
     * Static tags
     *
     * Static tags that are sent to Sentry with every event.
     */
    @ConfigItem
    public Map<String, String> tags;

    public SentryProxyConfig proxy;
}

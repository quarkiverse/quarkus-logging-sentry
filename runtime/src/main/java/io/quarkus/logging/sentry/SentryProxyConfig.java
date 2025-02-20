package io.quarkus.logging.sentry;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithParentName;

@ConfigGroup
public interface SentryProxyConfig {

    /**
     * Determine whether to enable a Proxy for all Sentry outbound requests. This is also used for HTTPS
     * requests.
     *
     * @deprecated we try to stay away from this pattern now, replace with {@code quarkus.log.sentry.enabled}.
     */
    @Deprecated(forRemoval = true)
    @WithParentName
    Optional<Boolean> enable();

    /**
     * Determine whether to enable a Proxy for all Sentry outbound requests. This is also used for HTTPS
     * requests.
     */
    @WithDefault("false")
    boolean enabled();

    /**
     * Sets the host name of the proxy server.
     */
    public Optional<String> host();

    /**
     * Sets the port number of the proxy server
     */
    public Optional<Integer> port();

    /**
     * Sets the username to authenticate on the proxy server
     */
    public Optional<String> username();

    /**
     * Sets the password to authenticate on the proxy server
     */
    public Optional<String> password();
}

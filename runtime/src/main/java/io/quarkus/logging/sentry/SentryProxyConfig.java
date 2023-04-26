package io.quarkus.logging.sentry;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class SentryProxyConfig {
    /**
     * Determine whether to enable a Proxy for all Sentry outbound requests. This is also used for HTTPS
     * requests.
     */
    @ConfigItem(name = ConfigItem.PARENT)
    public boolean enable;

    /**
     * Sets the host name of the proxy server.
     */
    @ConfigItem
    public Optional<String> host;

    /**
     * Sets the port number of the proxy server
     */
    @ConfigItem
    public Optional<Integer> port;

    /**
     * Sets the username to authenticate on the proxy server
     */
    @ConfigItem
    public Optional<String> username;

    /**
     * Sets the password to authenticate on the proxy server
     */
    @ConfigItem
    public Optional<String> password;
}

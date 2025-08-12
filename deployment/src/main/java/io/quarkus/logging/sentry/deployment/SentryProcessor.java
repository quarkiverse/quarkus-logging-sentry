package io.quarkus.logging.sentry.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LogHandlerBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.logging.sentry.SentryHandlerValueFactory;
import io.sentry.Breadcrumb;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import io.sentry.SpanContext;
import io.sentry.SpanId;
import io.sentry.SpanStatus;
import io.sentry.protocol.*;

class SentryProcessor {

    private static final String FEATURE = "logging-sentry";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    LogHandlerBuildItem addSentryLogHandler(final SentryHandlerValueFactory sentryHandlerValueFactory) {
        return new LogHandlerBuildItem(sentryHandlerValueFactory.create());
    }

    @BuildStep
    ExtensionSslNativeSupportBuildItem activateSslNativeSupport() {
        return new ExtensionSslNativeSupportBuildItem(FEATURE);
    }

    @BuildStep
    ReflectiveClassBuildItem addReflection() {
        return ReflectiveClassBuildItem.builder(
                Breadcrumb.class.getName(),
                SentryBaseEvent.class.getName(),
                SentryEvent.class.getName(),
                "io.sentry.SentryValues",
                SpanContext.class.getName(),
                SpanStatus.class.getName(),
                SpanId.class.getName(),
                App.class.getName(),
                Browser.class.getName(),
                Contexts.class.getName(),
                DebugImage.class.getName(),
                DebugMeta.class.getName(),
                Device.class.getName(),
                Gpu.class.getName(),
                Mechanism.class.getName(),
                Message.class.getName(),
                OperatingSystem.class.getName(),
                Request.class.getName(),
                SdkInfo.class.getName(),
                SdkVersion.class.getName(),
                SentryException.class.getName(),
                SentryId.class.getName(),
                SentryPackage.class.getName(),
                SentryRuntime.class.getName(),
                SentryStackFrame.class.getName(),
                SentryStackTrace.class.getName(),
                SentryThread.class.getName(),
                SentryTransaction.class.getName(),
                SentrySpan.class.getName(),
                SentryOptions.BeforeSendCallback.class.getName(),
                User.class.getName())
                .methods(true)
                .fields(true)
                .build();
    }
}

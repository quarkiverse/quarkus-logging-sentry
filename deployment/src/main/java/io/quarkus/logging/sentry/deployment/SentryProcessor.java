package io.quarkus.logging.sentry.deployment;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTransformation;
import org.jboss.jandex.DotName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LogHandlerBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.logging.sentry.SentryHandlerValueFactory;
import io.quarkus.logging.sentry.SentryInterceptor;
import io.quarkus.logging.sentry.SentryHttp;
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
    private static final Logger log = LoggerFactory.getLogger(SentryProcessor.class);

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
    AnnotationsTransformerBuildItem registerInterceptors() {
        DotName path = DotName.createSimple("jakarta.ws.rs.Path");

        return new AnnotationsTransformerBuildItem(AnnotationTransformation.forClasses()
                .when(transformationContext -> transformationContext
                        .hasAnnotation(DotName.createSimple("jakarta.ws.rs.Path")))
                .transform(
                        transformationContext -> {
                            String string = transformationContext.declaration().asClass().annotation(path).value()
                                    .asString();
                            transformationContext.add(
                                    AnnotationInstance.builder(SentryHttp.class)
                                            .value(string).build());
                        }));
    }

    @BuildStep
    AdditionalBeanBuildItem loggingInterceptor() {
        return AdditionalBeanBuildItem.unremovableOf(SentryInterceptor.class);
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

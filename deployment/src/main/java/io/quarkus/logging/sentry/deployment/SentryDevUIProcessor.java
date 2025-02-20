package io.quarkus.logging.sentry.deployment;

import java.util.Objects;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devui.spi.page.CardPageBuildItem;
import io.quarkus.devui.spi.page.ExternalPageBuilder;
import io.quarkus.devui.spi.page.Page;
import io.sentry.Sentry;

/**
 * Dev UI card for displaying important details such as the Sentry library version.
 */
public class SentryDevUIProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    void createVersion(BuildProducer<CardPageBuildItem> cardPageBuildItemBuildProducer) {
        final CardPageBuildItem card = new CardPageBuildItem();

        final ExternalPageBuilder versionPage = Page.externalPageBuilder("Sentry Version")
                .icon("font-awesome-solid:tag")
                .url("https://sentry.io/")
                .doNotEmbed()
                .staticLabel(Objects.toString(Sentry.class.getPackage().getImplementationVersion(), "?"));

        card.addPage(versionPage);

        card.setCustomCard("qwc-logging-sentry-card.js");

        cardPageBuildItemBuildProducer.produce(card);
    }
}

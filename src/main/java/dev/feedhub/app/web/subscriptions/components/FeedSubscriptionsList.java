package dev.feedhub.app.web.subscriptions.components;

import org.springframework.data.domain.Page;

import dev.feedhub.app.subscriptions.FeedSubscription;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.table;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table;

public class FeedSubscriptionsList {

  public static DomContent feedSubscriptions(Page<FeedSubscription> feedSubscriptions) {

    return div().withId("subscriptions").withClasses("mx-2").with(

        h3().withText("All subscriptions"),

        div().with(

            table().withClasses(table, table_striped).with(

                thead().with(

                    tr().with(

                        th("Subscriptions")

                    )

                ),

                tbody().with(

                    each(feedSubscriptions.getContent(),
                        feedConfiguration -> feedSubscriptionRow(
                          feedConfiguration))

                )

            )

        )

    );
  }

  private static TrTag feedSubscriptionRow(FeedSubscription feedSubscription) {
    return tr().with(

        td().with(text(feedSubscription.feedId().id()))

    );
  }

}

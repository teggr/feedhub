package dev.feedhub.app.web.subscriptions.components;

import dev.feedhub.app.subscriptions.Subscription;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;
import org.springframework.data.domain.Page;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table_striped;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.td;
import static j2html.TagCreator.text;
import static j2html.TagCreator.th;
import static j2html.TagCreator.thead;
import static j2html.TagCreator.tr;

public class FeedSubscriptionsList {

  public static DomContent feedSubscriptions(Page<Subscription> feedSubscriptions) {

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

  private static TrTag feedSubscriptionRow(Subscription feedSubscription) {
    return tr().with(

      td().with(text(feedSubscription.feedId().id()))

    );
  }

}

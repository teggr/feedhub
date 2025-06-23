package dev.feedhub.app.web.admin.subscriptions.components;

import dev.feedhub.app.subscriptions.Subscriber;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;
import org.springframework.data.domain.Page;

import static dev.feedhub.app.web.utils.TimeUtils.formatInstant;
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

public class FeedSubscriptionsAdminList {

  public static DomContent feedSubscriptions(Page<Subscriber> feedSubscribers) {

    return div().withId("feedSubscribers").withClasses("mx-2").with(

      h3().withText("All subscribers"),

      div().with(

        table().withClasses(table, table_striped).with(

          thead().with(

            tr().with(

              th("Subscriber ID"),

              th("Created Date")

            )

          ),

          tbody().with(

            each(feedSubscribers.getContent(),
              feedSubscriber -> feedSubscriberRow(feedSubscriber))

          )

        )

      )

    );
  }

  private static TrTag feedSubscriberRow(Subscriber feedSubscriber) {
    return tr().with(

      td().with(text(feedSubscriber.subscriberId())),
      td().with(text(formatInstant(feedSubscriber.createdDate())))

    );
  }

}

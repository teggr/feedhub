package dev.feedhub.app.web.admin.subscriptions.components;

import org.springframework.data.domain.Page;

import dev.feedhub.app.subscriptions.FeedSubscriber;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.table;
import static dev.feedhub.app.web.utils.TimeUtils.formatInstant;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table;

public class FeedSubscriptionsAdminList {

  public static DomContent feedSubscriptions(Page<FeedSubscriber> feedSubscribers) {

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

  private static TrTag feedSubscriberRow(FeedSubscriber feedSubscriber) {
    return tr().with(

        td().with(text(feedSubscriber.subscriberId())), 
        td().with(text(formatInstant(feedSubscriber.createdDate())))

    );
  }

}

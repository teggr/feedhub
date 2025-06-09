package dev.feedhub.app.web.feeds.components;

import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.web.feeds.FeedUrlBuilder;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedUrlBuilder;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.table;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table;

public class FeedsList {

  public static DomContent feeds(CsrfToken csrfToken, Page<Feed> feeds, FeedUrlBuilder feedUrlBuilder, SubscribeToFeedUrlBuilder subscribeToFeedUrlBuilder) {

    return div().withId("feeds").withClasses("mx-2").with(

        h3().withText("All feeds"),

        div().with(

            table().withClasses(table, table_striped).with(

                thead().with(

                    tr().with(

                        th("Feed URL"), 

                        th("Title"),

                        th("Subscribe")

                    )

                ),

                tbody().with(

                    each(feeds.getContent(),
                        feed -> feedRow(
                          csrfToken, feed, feedUrlBuilder, subscribeToFeedUrlBuilder))

                )

            )

        )

    );
  }

  private static TrTag feedRow(CsrfToken csrfToken, Feed feed, FeedUrlBuilder feedUrlBuilder, SubscribeToFeedUrlBuilder subscribeToFeedUrlBuilder) {
    return tr().with(

        td().with(text(feed.url().toString())), 
        td().with(a().withHref( feedUrlBuilder.build(feed.feedId()) ).withText(feed != null ? feed.title() : "")),
        td().with(
          form().withMethod("post").withAction(subscribeToFeedUrlBuilder.build(feed.feedId())).with(
            input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),
            button().withType("submit").withText("Subscribe").withClasses(btn, btn_primary)
          )
        )

    );
  }

}

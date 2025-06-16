package dev.feedhub.app.web.feeds.components;

import org.springframework.data.domain.Page;

import dev.feedhub.app.feeds.FeedItem;
import dev.feedhub.app.web.utils.TimeUtils;
import j2html.tags.DomContent;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.h6;
import static j2html.TagCreator.small;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;

public class FeedItemsList {

  public static DomContent feeds(Page<FeedItem> feedsItems) {

    return div().withId("feeds").withClasses("mx-2").with(

        h3().withText("All items"),

        div().with(

          each(feedsItems.getContent(), feedItem -> 

            div().withClasses(card).with(
              div().withClasses(card_body).with(
                div().withClasses(d_flex).with(
                  div().withClasses(flex_shrink_0).with(
                    img().withSrc(feedItem.imageUrl()).withClasses(img_thumbnail).withWidth("100")
                  ),
                  div().withClasses(flex_grow_1, ms_3).with(
                    h6(feedItem.title()).withClasses(card_title),
                    p().withClasses(card_text).with(
                      small(TimeUtils.formatInstant(feedItem.publishedDate())).withClass(text_body_secondary)
                    ),
                    div().withClasses(card_text).with(rawHtml(feedItem.description()))
                  )
                )
              )
            )

          )

        )
    );
  }

}

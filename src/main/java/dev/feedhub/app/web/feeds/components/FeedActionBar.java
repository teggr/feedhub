package dev.feedhub.app.web.feeds.components;


import j2html.tags.specialized.HeaderTag;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

import dev.feedhub.app.feeds.Feed;

public class FeedActionBar {

  public static HeaderTag feedActionBar(Feed feed, String feedsUrl) {

    return header().withClasses(row).with(

        div().withClass(col_2).with(h2(feed.title()).withClasses(px_3)),

        div().withClasses(col, pt_1).with(

            a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-return-left"))
                .withHref(feedsUrl)

        ));

  }

}

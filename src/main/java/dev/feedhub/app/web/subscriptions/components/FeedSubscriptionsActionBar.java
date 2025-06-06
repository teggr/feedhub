package dev.feedhub.app.web.subscriptions.components;


import j2html.tags.specialized.HeaderTag;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

public class FeedSubscriptionsActionBar {

  public static HeaderTag feedSubscriptionsActionBar(String refreshUrl) {

    return header().withClasses(row).with(

        div().withClass(col_2).with(h2("Subscriptions").withClasses(px_3)),

        div().withClasses(col, pt_1).with(

            a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-clockwise"))
                .withHref(refreshUrl)

        ));

  }

}

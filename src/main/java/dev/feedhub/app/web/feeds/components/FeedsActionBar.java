package dev.feedhub.app.web.feeds.components;


import j2html.tags.specialized.HeaderTag;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

import org.springframework.security.core.userdetails.User;

public class FeedsActionBar {

  public static HeaderTag feedsActionBar(User user, String refreshUrl, String feedsAdminUrl) {

    return header().withClasses(row).with(

        div().withClass(col_2).with(h2("Feeds").withClasses(px_3)),

        div().withClasses(col, pt_1).with(

            a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-clockwise"))
                .withHref(refreshUrl),

            iff(user != null,  a().withClasses(btn, btn_secondary).withText("Manage Feeds").withHref(feedsAdminUrl) )

        ));

  }

}

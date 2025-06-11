package dev.feedhub.app.web.feeds.components;


import j2html.tags.DomContent;
import j2html.tags.specialized.HeaderTag;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;

import dev.feedhub.app.feeds.Feed;

public class FeedActionBar {

  public static HeaderTag feedActionBar(User user, Feed feed, String feedsUrl, String addFeedUrl, CsrfToken csrfToken) {

    return header().withClasses(row).with(

        div().withClass(col_2).with(h2(feed.title()).withClasses(px_3)),

        div().withClasses(col, pt_1).with(

            a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-return-left"))
                .withHref(feedsUrl),

            iff( user != null ,  form().withMethod("post").withAction(addFeedUrl).withClasses(d_inline_flex, mb_0).with(

                input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

                div().withClasses(form_control, me_2).with(input().withType("url").withName("url")),

                button().withType("submit").withClasses(btn, btn_primary).withText("Add")
                
              )

            )

        )
    );

  }

}

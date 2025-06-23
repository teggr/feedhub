package dev.feedhub.app.web.admin.feeds.components;

import j2html.tags.specialized.HeaderTag;
import org.springframework.security.web.csrf.CsrfToken;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

public class FeedsAdminActionBar {

  public static HeaderTag feedsActionBar(CsrfToken csrfToken, String refreshUrl, String addFeedUrl, String runFetchFeedJobUrl, String feedsUrl) {

    return header().withClasses(row).with(

      div().withClass(col_2).with(h2("Feeds").withClasses(px_3)),

      div().withClasses(col, pt_1).with(

        a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-clockwise"))
          .withHref(refreshUrl),

        form().withMethod("post").withAction(addFeedUrl).withClasses(d_inline_flex, mb_0).with(

          input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

          div().withClasses(form_control, me_2).with(input().withType("url").withName("url")),

          button().withType("submit").withClasses(btn, btn_primary).withText("Add")),

        form().withMethod("post").withAction(runFetchFeedJobUrl).withClasses(d_inline_flex, mb_0).with(

          input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

          button().withType("submit").withClasses(btn, btn_primary).withText("Run Update Job")),

        a().withClasses(btn, btn_secondary).withText("Browse Feeds")
          .withHref(feedsUrl)

      ));

  }

}

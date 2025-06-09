package dev.feedhub.app.web.admin.subscriptions.components;

import j2html.tags.specialized.HeaderTag;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h2;

import org.springframework.security.web.csrf.CsrfToken;

public class FeedSubscriptionsAdminActionBar {

  public static HeaderTag feeSubscriptionsActionBar(CsrfToken csrfToken, String refreshUrl, String addSubscriberUrl) {

    return header().withClasses(row).with(

        div().withClass(col_2).with(h2("Subscriptions").withClasses(px_3)),

        div().withClasses(col, pt_1).with(

            a().withClasses(btn, btn_secondary).with(span().withClasses("bi", "bi-arrow-clockwise"))
                .withHref(refreshUrl),
            
            form().withMethod("post").withAction(addSubscriberUrl).withClasses(d_inline_flex, mb_0).with(

                input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

                button().withType("submit").withClasses(btn, btn_primary).withText("Create Subscriber"))

        ));

  }

}

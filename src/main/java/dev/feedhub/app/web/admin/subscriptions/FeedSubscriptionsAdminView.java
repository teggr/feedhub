package dev.feedhub.app.web.admin.subscriptions;

import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.web.admin.subscriptions.components.FeedSubscriptionsAdminActionBar;
import dev.feedhub.app.web.admin.subscriptions.components.FeedSubscriptionsAdminList;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;

@Component
public class FeedSubscriptionsAdminView extends J2HtmlView {

  @SuppressWarnings({"unchecked", "null"})
  @Override
  protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    // get from the model
    Page<Subscriber> feedSubscribers = (Page<Subscriber>) model.get("feedSubscribers");

    String refreshUrl = (String) model.get("refreshUrl");
    String addSubscriberUrl = (String) model.get("addSubscriberUrl");

    CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

    // build the ui
    return FeedHubSiteLayout.add("FeedHub | Admin Subscriptions", model,

      each(

        FeedHubNavigation.feedHubNavigation(model),

        div().withClasses(container_fluid).with(

          FeedSubscriptionsAdminActionBar.feeSubscriptionsActionBar(csrfToken, refreshUrl, addSubscriberUrl),

          hr(),

          div().withClasses(row).with(

            div().withClasses(col).with(
              FeedSubscriptionsAdminList.feedSubscriptions(feedSubscribers)
            )

          )

        )

      ));

  }

}

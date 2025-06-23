package dev.feedhub.app.web.feeds;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.web.feeds.components.FeedsActionBar;
import dev.feedhub.app.web.feeds.components.FeedsList;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedUrlBuilder;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;

@Component
public class FeedsView extends J2HtmlView {

  @SuppressWarnings({"unchecked", "null"})
  @Override
  protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    // get from the model
    Page<Feed> feeds = (Page<Feed>) model.get("feeds");

    String refreshUrl = (String) model.get("refreshUrl");
    String feedsAdminUrl = (String) model.get("feedsAdminUrl");
    String addFeedUrl = (String) model.get("addFeedUrl");

    User user = (User) model.get("user");
    Subscriber subscriber = (Subscriber) model.get("subscriber");

    SubscribeToFeedUrlBuilder subscribeToFeedUrlBuilder = (SubscribeToFeedUrlBuilder) model.get("subscribeToFeedUrlBuilder");
    FeedUrlBuilder feedUrlBuilder = (FeedUrlBuilder) model.get("feedUrlBuilder");

    CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

    // build the ui
    return FeedHubSiteLayout.add("FeedHub | Feeds", model,

      each(

        FeedHubNavigation.feedHubNavigation(model),

        div().withClasses(container_fluid).with(

          FeedsActionBar.feedsActionBar(user, refreshUrl, feedsAdminUrl, addFeedUrl, csrfToken),

          hr(),

          div().withClasses(row).with(

            div().withClasses(col).with(
              FeedsList.feeds(csrfToken, feeds, feedUrlBuilder, subscriber, subscribeToFeedUrlBuilder)
            )

          )

        )

      ));

  }

}

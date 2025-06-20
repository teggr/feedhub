package dev.feedhub.app.web.feeds;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.web.feeds.components.FeedsActionBar;
import dev.feedhub.app.web.feeds.components.FeedsList;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedUrlBuilder;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static j2html.TagCreator.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;

@Component
public class FeedsView extends AbstractView {

  @Override
  @Nullable
  public String getContentType() {
    return MediaType.TEXT_HTML_VALUE;
  }

  @SuppressWarnings({ "unchecked", "null" })
  @Override
  protected void renderMergedOutputModel(@Nullable Map<String, Object> model, @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response) throws Exception {

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
    DomContent html = FeedHubSiteLayout.add("FeedHub | Feeds", model,

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

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}

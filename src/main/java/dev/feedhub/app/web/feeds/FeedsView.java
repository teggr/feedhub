package dev.feedhub.app.web.feeds;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import dev.feedhub.app.feeds.Feed;
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

    SubscribeToFeedUrlBuilder subscribeToFeedUrlBuilder = (SubscribeToFeedUrlBuilder) model.get("subscribeToFeedUrlBuilder");
    FeedUrlBuilder feedUrlBuilder = (FeedUrlBuilder) model.get("feedUrlBuilder");

    // build the ui
    DomContent html = FeedHubSiteLayout.add("FeedHub | Feeds", model,

      each(

          FeedHubNavigation.feedHubNavigation(model),

          div().withClasses(container_fluid).with(

              FeedsActionBar.feedsActionBar(refreshUrl, feedsAdminUrl),

              hr(),

              div().withClasses(row).with(

                div().withClasses(col).with(
                  FeedsList.feeds(feeds, feedUrlBuilder, subscribeToFeedUrlBuilder)
                )

              )

          )

      ));

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}

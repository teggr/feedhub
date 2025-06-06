package dev.feedhub.app.web.admin.feeds;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.feeds.FeedConfiguration;
import dev.feedhub.app.scheduler.ScheduledJob;
import dev.feedhub.app.web.admin.feeds.components.FeedsAdminActionBar;
import dev.feedhub.app.web.admin.feeds.components.FeedsAdminList;
import dev.feedhub.app.web.feeds.FeedUrlBuilder;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static j2html.TagCreator.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;

@Component
public class FeedsAdminView extends AbstractView {

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
    Page<FeedConfiguration> feedConfigurations = (Page<FeedConfiguration>) model.get("feedConfigurations");
    List<ScheduledJob> scheduledFetchFeedJobs = (List<ScheduledJob>) model.get("scheduledFetchFeedJobs");
    List<Feed> feeds = (List<Feed>) model.get("feeds");
    
    String addFeedUrl = (String) model.get("addFeedUrl");
    String refreshUrl = (String) model.get("refreshUrl");
    String runFetchFeedJobUrl = (String) model.get("runFetchFeedJobUrl");
    String feedsUrl = (String) model.get("feedsUrl");
    FeedUrlBuilder feedUrlBuilder = (FeedUrlBuilder) model.get("feedUrlBuilder");
    FetchFeedUrlBuilder fetchFeedUrlBuilder = (FetchFeedUrlBuilder) model.get("fetchFeedUrlBuilder");

    // build the ui
    DomContent html = FeedHubSiteLayout.add("FeedHub | Admin Feeds", model,

      each(

          FeedHubNavigation.feedHubNavigation(model),

          div().withClasses(container_fluid).with(

              FeedsAdminActionBar.feedsActionBar(refreshUrl, addFeedUrl, runFetchFeedJobUrl, feedsUrl),

              hr(),

              div().withClasses(row).with(

                div().withClasses(col).with(
                  FeedsAdminList.feeds(feedConfigurations, scheduledFetchFeedJobs, feeds, feedUrlBuilder, fetchFeedUrlBuilder)
                )

              )

          )

      ));

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}

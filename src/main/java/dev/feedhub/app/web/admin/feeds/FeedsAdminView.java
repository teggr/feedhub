package dev.feedhub.app.web.admin.feeds;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.feeds.FeedConfiguration;
import dev.feedhub.app.scheduler.ScheduledJob;
import dev.feedhub.app.web.admin.feeds.components.FeedsAdminActionBar;
import dev.feedhub.app.web.admin.feeds.components.FeedsAdminList;
import dev.feedhub.app.web.feeds.FeedUrlBuilder;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;

@Component
public class FeedsAdminView extends J2HtmlView {

  @SuppressWarnings({"unchecked", "null"})
  @Override
  protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

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

    CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

    // build the ui
    return FeedHubSiteLayout.add("FeedHub | Admin Feeds", model,

      each(

        FeedHubNavigation.feedHubNavigation(model),

        div().withClasses(container_fluid).with(

          FeedsAdminActionBar.feedsActionBar(csrfToken, refreshUrl, addFeedUrl, runFetchFeedJobUrl, feedsUrl),

          hr(),

          div().withClasses(row).with(

            div().withClasses(col).with(
              FeedsAdminList.feeds(csrfToken, feedConfigurations, scheduledFetchFeedJobs, feeds, feedUrlBuilder, fetchFeedUrlBuilder)
            )

          )

        )

      ));

  }

}

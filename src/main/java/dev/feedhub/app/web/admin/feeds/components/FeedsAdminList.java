package dev.feedhub.app.web.admin.feeds.components;

import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.feeds.FeedConfiguration;
import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.scheduler.ScheduledJob;
import dev.feedhub.app.web.admin.feeds.FetchFeedUrlBuilder;
import dev.feedhub.app.web.feeds.FeedUrlBuilder;
import j2html.tags.DomContent;
import j2html.tags.specialized.TrTag;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.table;
import static dev.feedhub.app.web.utils.TimeUtils.formatInstant;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.table;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedsAdminList {

  public static DomContent feeds(CsrfToken csrfToken, Page<FeedConfiguration> feedConfigurations, List<ScheduledJob> scheduledFetchFeedJobs,
      List<Feed> feeds, FeedUrlBuilder feedUrlBuilder, FetchFeedUrlBuilder fetchFeedUrlBuilder) {

    Map<FeedId, ScheduledJob> scheduledFetchFeedJobsByFeedId = scheduledFetchFeedJobs.stream()
        .collect(Collectors.toMap(ScheduledJob::feedId, job -> job));
    Map<FeedId, Feed> feedsByFeedId = feeds.stream().collect(Collectors.toMap(Feed::feedId, feed -> feed));

    return div().withId("feeds").withClasses("mx-2").with(

        h3().withText("All feeds"),

        div().with(

            table().withClasses(table, table_striped).with(

                thead().with(

                    tr().with(

                        th("Feed URL"), 

                        th("Title"), 
                        
                        th("Created Date"), 
                        
                        th("Scheduler"), 
                        
                        th("Next Scheduled Date"),

                        th("Last Scheduled Date"), 
                        
                        th("Last Scheduled Result"), 
                        
                        th("")

                    )

                ),

                tbody().with(

                    each(feedConfigurations.getContent(),
                        feedConfiguration -> feedRow(
                          csrfToken,
                          feedConfiguration, 
                          feedsByFeedId.get(feedConfiguration.feedId()), 
                          scheduledFetchFeedJobsByFeedId.get(feedConfiguration.feedId()),
                          feedUrlBuilder, fetchFeedUrlBuilder))

                )

            )

        )

    );
  }

  private static TrTag feedRow(CsrfToken csrfToken, FeedConfiguration feedConfiguration, Feed feed, ScheduledJob scheduledFetchFeedJob, FeedUrlBuilder feedUrlBuilder, FetchFeedUrlBuilder fetchFeedUrlBuilder) {
    return tr().with(

        td().with(text(feedConfiguration.url().toString())), 
        td().with(text(feed != null ? feed.title() : "")),
        td().with(text(formatInstant(feedConfiguration.createdDate()))),
        td().with(text(feedConfiguration.schedule().toString())),
        td().with(text(scheduledFetchFeedJob != null ? formatInstant(scheduledFetchFeedJob.nextScheduledRun()) : "")),
        // div().withClasses(col).with(strong().withText(feed.title()),
        // span(feed.summary()).withClasses()),
        td().with(text(scheduledFetchFeedJob != null && scheduledFetchFeedJob.lastScheduledRun() != null
            ? formatInstant(scheduledFetchFeedJob.lastScheduledRun())
            : "")),
        td().with(text(scheduledFetchFeedJob != null && scheduledFetchFeedJob.lastScheduledRunResult() != null
            ? scheduledFetchFeedJob.lastScheduledRunResult().toString()
            : "")),
        td().with(
          a().withHref(feedUrlBuilder.build(feedConfiguration.feedId()))
              .withText("View")
              .withClasses(btn, btn_sm, btn_outline_info),
          form().withMethod("post")
          .withAction(fetchFeedUrlBuilder.build(feedConfiguration.feedId()))
          .with(
            input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),
            button().withType("submit").withText("Fetch")
              .withClasses(btn, btn_sm, btn_outline_info, d_inline)
          )   
        )

    );
  }

}

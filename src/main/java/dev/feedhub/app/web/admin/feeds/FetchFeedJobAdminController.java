package dev.feedhub.app.web.admin.feeds;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.fetch.FetchFeedJobScheduler;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/fetch-feeds")
@RequiredArgsConstructor
public class FetchFeedJobAdminController {

  private final FetchFeedJobScheduler fetchFeedJobScheduler;

  @PostMapping
  public String postRunJob() {
    fetchFeedJobScheduler.runNextScheduled();
    return "redirect:/admin/feeds";
  }

  @PostMapping("/{feedId}")
  public String postRunFeedJob(@PathParam("feedId") String feedIdValue) {
    FeedId feedId = new FeedId(feedIdValue);
    fetchFeedJobScheduler.run(feedId);
    return "redirect:/admin/feeds";
  }
  

}

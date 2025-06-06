package dev.feedhub.app.web.admin.feeds;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.feedhub.app.feeds.FeedConfigurations;
import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.feeds.Feeds;
import dev.feedhub.app.fetch.FetchFeedJobScheduler;
import dev.feedhub.app.web.feeds.FeedUrlBuilder;
import dev.feedhub.app.web.feeds.FeedsController;
import lombok.RequiredArgsConstructor;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import java.net.URL;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/feeds")
@RequiredArgsConstructor
public class FeedsAdminController {

  private final FeedConfigurations feedConfigurations;
  private final FetchFeedJobScheduler feedUpdateJobScheduler;
  private final Feeds feeds;

  @GetMapping
  public String getFeeds(Pageable pageable, Model model) {

    String refreshUrl = fromMethodName(FeedsAdminController.class, "getFeeds", pageable, model).build().toUriString();
    model.addAttribute("refreshUrl", refreshUrl);

    String addFeedUrl = fromMethodName(FeedsAdminController.class, "postFeed", null, null).build().toUriString();
    model.addAttribute("addFeedUrl", addFeedUrl);

    String runFetchFeedJobUrl = fromMethodName(FetchFeedJobAdminController.class, "postRunJob").build().toUriString();
    model.addAttribute("runFetchFeedJobUrl", runFetchFeedJobUrl);

    String feedsUrl = fromMethodName(FeedsController.class, "getFeeds", null, null).build().toUriString();
    model.addAttribute("feedsUrl", feedsUrl);

    model.addAttribute("feedUrlBuilder", new FeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
        return fromMethodName(FeedsController.class, "getFeed", feedId.id(), null, null).build().toUriString();
      }
    });

    model.addAttribute("fetchFeedUrlBuilder", new FetchFeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
          return fromMethodName(FetchFeedJobAdminController.class, "postRunFeedJob", feedId.id()).build().toUriString();
      }
    });

    model.addAttribute("feedConfigurations", feedConfigurations.getFeedConfigurations(pageable));
    model.addAttribute("scheduledFetchFeedJobs", feedUpdateJobScheduler.getScheduledFetchFeedJobs());
     model.addAttribute("feeds", feeds.getFeeds());
    
    return "feedsAdminView";
  }

  @PostMapping
  public String postFeed(@RequestParam(value = "url", required = false) URL url,
      RedirectAttributes redirectAttributes) {

    FeedId feedId = feedConfigurations.createFeedConfiguration(url);

    // redirectAttributes.addAttribute("feedId", feedId.id());

    return "redirect:/admin/feeds";

  }

}

package dev.feedhub.app.web.feeds;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.feeds.Feeds;
import dev.feedhub.app.subscriptions.FeedSubscriber;
import dev.feedhub.app.subscriptions.FeedSubscriberRepository;
import dev.feedhub.app.web.admin.feeds.FeedsAdminController;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedController;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedUrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@Controller
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedsController {

  private final Feeds feeds;
  private final FeedSubscriberRepository feedSubscriberRepository;

  @GetMapping
  public String getFeeds(Pageable pageable, Model model) {

    String feedsAdminUrl = fromMethodName(FeedsAdminController.class, "getFeeds", pageable, model).build().toUriString();
    model.addAttribute("feedsAdminUrl", feedsAdminUrl);

    String refreshUrl = fromMethodName(FeedsController.class, "getFeeds", pageable, model).build().toUriString();
    model.addAttribute("refreshUrl", refreshUrl);

    model.addAttribute("feedUrlBuilder", new FeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
        return fromMethodName(FeedsController.class, "getFeed", feedId.id(), null, null).build().toUriString();
      }
    });

    FeedSubscriber firstSubscriber = feedSubscriberRepository.findAll().getFirst();
    model.addAttribute("subscribeToFeedUrlBuilder", new SubscribeToFeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
        return fromMethodName(SubscribeToFeedController.class, "postSubscribeToFeed", firstSubscriber.subscriberId(), feedId.id()).build().toUriString();
      }
    });

    model.addAttribute("feeds", feeds.getFeeds(pageable));

    return "feedsView";

  }

  @GetMapping("/{feedId}")
  public String getFeed(@PathVariable("feedId") String feedIdValue, Pageable pageable, Model model) {

    FeedId feedId = new FeedId(feedIdValue);

    String feedsUrl = fromMethodName(FeedsController.class, "getFeeds", null, null).build().toUriString();
    model.addAttribute("feedsUrl", feedsUrl);

    FeedSubscriber firstSubscriber = feedSubscriberRepository.findAll().getFirst();
    model.addAttribute("subscribeToFeedUrlBuilder", new SubscribeToFeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
        return fromMethodName(SubscribeToFeedController.class, "postSubscribeToFeed", firstSubscriber.subscriberId(), feedId.id()).build().toUriString();
      }
    });

    model.addAttribute("feed", feeds.getFeed(feedId));
    model.addAttribute("feedItems", feeds.getFeedItems(feedId, pageable));

    return "feedView";

  }

}

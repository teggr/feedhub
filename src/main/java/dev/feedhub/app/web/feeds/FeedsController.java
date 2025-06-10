package dev.feedhub.app.web.feeds;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.feeds.Feeds;
import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.subscriptions.Subscriptions;
import dev.feedhub.app.subscriptions.SubscriberRepository;
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
  private final Subscriptions subscriptions;

  @GetMapping
  public Object getFeeds(@AuthenticationPrincipal User user, Pageable pageable, Model model) {

    String refreshUrl = fromMethodCall(on(FeedsController.class).getFeeds(user, pageable, model)).build().toUriString();
    model.addAttribute("refreshUrl", refreshUrl);

    model.addAttribute("feedUrlBuilder", new FeedUrlBuilder() {
      @Override
      public String build(FeedId feedId) {
        return fromMethodCall(on(FeedsController.class).getFeed(feedId.id(), user, null, null)).build().toUriString();
      }
    });

    if(user != null) {
      
      Subscriber subscriber = subscriptions.getSubscriberForUser( user );
      model.addAttribute("subscriber", subscriber);

      model.addAttribute("subscribeToFeedUrlBuilder", new SubscribeToFeedUrlBuilder() {
        @Override
        public String build(FeedId feedId) {
          return fromMethodCall(on(SubscribeToFeedController.class).postSubscribeToFeed(subscriber.subscriberId(), feedId.id())).build().toUriString();
        }
      });
      
    }

    model.addAttribute("feeds", feeds.getFeeds(pageable));

    return "feedsView";

  }

  @GetMapping("/{feedId}")
  public Object getFeed(@PathVariable("feedId") String feedIdValue, @AuthenticationPrincipal User user, Pageable pageable, Model model) {

    FeedId feedId = new FeedId(feedIdValue);

    if(user != null) {
      
      Subscriber subscriber = subscriptions.getSubscriberForUser( user );
      model.addAttribute("subscriber", subscriber);

      model.addAttribute("subscribeToFeedUrlBuilder", new SubscribeToFeedUrlBuilder() {
        @Override
        public String build(FeedId feedId) {
          return fromMethodCall(on(SubscribeToFeedController.class).postSubscribeToFeed(subscriber.subscriberId(), feedId.id())).build().toUriString();
        }
      });

    }

    model.addAttribute("feed", feeds.getFeed(feedId));
    model.addAttribute("feedItems", feeds.getFeedItems(feedId, pageable));

    return "feedView";

  }

}

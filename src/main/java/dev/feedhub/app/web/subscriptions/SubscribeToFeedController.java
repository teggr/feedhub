package dev.feedhub.app.web.subscriptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.subscriptions.Subscriptions;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/subscribe-to-feed")
@RequiredArgsConstructor
public class SubscribeToFeedController {

    private final Subscriptions subscriptions;

    @PostMapping
    public Object postSubscribeToFeed( @RequestParam("subscriberId") String subscriberId, @RequestParam("feedId") String feedIdValue ) {
        
        subscriptions.subscribeToFeed( subscriberId, new FeedId(feedIdValue));

        return "redirect:/subscriptions";
        
    }

}

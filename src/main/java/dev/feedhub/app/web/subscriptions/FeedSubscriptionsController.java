package dev.feedhub.app.web.subscriptions;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.feedhub.app.subscriptions.FeedSubscriber;
import dev.feedhub.app.subscriptions.FeedSubscriberRepository;
import dev.feedhub.app.subscriptions.FeedSubscriptions;
import lombok.RequiredArgsConstructor;


import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class FeedSubscriptionsController {

    private final FeedSubscriptions feedSubscriptions;
    private final FeedSubscriberRepository feedSubscriberRepository;

    @GetMapping
    public String getSubscriptions(Pageable pageable, Model model) {
        String refreshUrl = fromMethodName(FeedSubscriptionsController.class, "getSubscriptions", pageable, model).build().toUriString();
        model.addAttribute("refreshUrl", refreshUrl);       

        FeedSubscriber firstSubscriber = feedSubscriberRepository.findAll().getFirst();

        model.addAttribute("feedSubscriptions", feedSubscriptions.getFeedSubscriptions(firstSubscriber.subscriberId(), pageable));

        // TODO: need to add view

        return "feedSubscriptionsView";

    }


}

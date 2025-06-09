package dev.feedhub.app.web.subscriptions;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.subscriptions.FeedSubscriberRepository;
import dev.feedhub.app.subscriptions.Subscriptions;
import lombok.RequiredArgsConstructor;


import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class FeedSubscriptionsController {

    private final Subscriptions subscriptions;
    private final FeedSubscriberRepository feedSubscriberRepository;

    @GetMapping
    public String getSubscriptions(Pageable pageable, Model model) {
        String refreshUrl = fromMethodName(FeedSubscriptionsController.class, "getSubscriptions", pageable, model).build().toUriString();
        model.addAttribute("refreshUrl", refreshUrl);       

        Subscriber firstSubscriber = feedSubscriberRepository.findAll().getFirst();

        model.addAttribute("feedSubscriptions", subscriptions.getFeedSubscriptions(firstSubscriber.subscriberId(), pageable));

        // TODO: need to add view

        return "feedSubscriptionsView";

    }


}

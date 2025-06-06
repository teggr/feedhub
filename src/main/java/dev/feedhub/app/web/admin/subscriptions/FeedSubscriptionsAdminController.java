package dev.feedhub.app.web.admin.subscriptions;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import dev.feedhub.app.subscriptions.FeedSubscriptions;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/subscriptions")
@RequiredArgsConstructor
public class FeedSubscriptionsAdminController {

    private final FeedSubscriptions feedSubscriptions;

    @GetMapping
    public String getSubscribers(Pageable pageable, Model model) {

        String refreshUrl = fromMethodName(FeedSubscriptionsAdminController.class, "getSubscribers", pageable, model).build().toUriString();
        model.addAttribute("refreshUrl", refreshUrl);

        String addSubscriberUrl = fromMethodName(FeedSubscriptionsAdminController.class, "postAddSubscriber").build().toUriString();
        model.addAttribute("addSubscriberUrl", addSubscriberUrl);
        
        model.addAttribute("feedSubscribers", feedSubscriptions.getSubscribers(pageable));

        return "feedSubscriptionsAdminView";

    }

    @PostMapping("/add-subscriber")
    public String postAddSubscriber() {
        
        feedSubscriptions.createSubscriber();
        
        return "redirect:/admin/subscriptions";

    }

}

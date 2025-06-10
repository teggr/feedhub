package dev.feedhub.app.web.admin.subscriptions;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import dev.feedhub.app.subscriptions.Subscriptions;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/subscriptions")
@RequiredArgsConstructor
public class FeedSubscriptionsAdminController {

    private final Subscriptions subscriptions;

    @GetMapping
    public Object getSubscribers(Pageable pageable, Model model) {

        String refreshUrl = fromMethodCall(on(FeedSubscriptionsAdminController.class).getSubscribers(pageable, model)).build().toUriString();
        model.addAttribute("refreshUrl", refreshUrl);

        String addSubscriberUrl = fromMethodCall(on(FeedSubscriptionsAdminController.class).postAddSubscriber()).build().toUriString();
        model.addAttribute("addSubscriberUrl", addSubscriberUrl);
        
        model.addAttribute("feedSubscribers", subscriptions.getSubscribers(pageable));

        return "feedSubscriptionsAdminView";

    }

    @PostMapping("/add-subscriber")
    public Object postAddSubscriber(  ) {
        
        // TODO: specify the user
        subscriptions.createSubscriber( "user" );
        
        return "redirect:/admin/subscriptions";

    }

}

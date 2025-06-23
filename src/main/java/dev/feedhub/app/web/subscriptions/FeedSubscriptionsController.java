package dev.feedhub.app.web.subscriptions;

import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.subscriptions.SubscriberRepository;
import dev.feedhub.app.subscriptions.Subscriptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class FeedSubscriptionsController {

  private final Subscriptions subscriptions;
  private final SubscriberRepository feedSubscriberRepository;

  @GetMapping
  public Object getSubscriptions(Pageable pageable, Model model) {

    String refreshUrl = fromMethodCall(on(FeedSubscriptionsController.class).getSubscriptions(pageable, model)).build().toUriString();
    model.addAttribute("refreshUrl", refreshUrl);

    Subscriber firstSubscriber = feedSubscriberRepository.findAll().getFirst();

    model.addAttribute("feedSubscriptions", subscriptions.getSubscriptions(firstSubscriber.subscriberId(), pageable));

    // TODO: need to add view

    return "feedSubscriptionsView";

  }


}

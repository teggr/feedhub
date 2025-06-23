package dev.feedhub.app.web.site;

import dev.feedhub.app.web.admin.feeds.FeedsAdminController;
import dev.feedhub.app.web.admin.subscriptions.FeedSubscriptionsAdminController;
import dev.feedhub.app.web.feeds.FeedsController;
import dev.feedhub.app.web.home.HomeController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@ControllerAdvice
public class FeedHubNavigationControllerAdvice {

  @ModelAttribute
  public void populateModel(Model model) {

    String homeUrl = fromMethodCall(on(HomeController.class).getHome(null, null)).build().toUriString();
    model.addAttribute("homeUrl", homeUrl);

    String feedsUrl = fromMethodCall(on(FeedsController.class).getFeeds(null, null, null)).build().toUriString();
    model.addAttribute("feedsUrl", feedsUrl);

    String feedsAdminUrl = fromMethodCall(on(FeedsAdminController.class).getFeeds(null, null)).build().toUriString();
    model.addAttribute("feedsAdminUrl", feedsAdminUrl);

    String feedSubscriptionsAdminUrl = fromMethodCall(on(FeedSubscriptionsAdminController.class).getSubscribers(null, null)).build().toUriString();
    model.addAttribute("feedSubscriptionsAdminUrl", feedSubscriptionsAdminUrl);

    String logoutUrl = "/logout";
    model.addAttribute("logoutUrl", logoutUrl);

    String loginUrl = "/login";
    model.addAttribute("loginUrl", loginUrl);

  }

}

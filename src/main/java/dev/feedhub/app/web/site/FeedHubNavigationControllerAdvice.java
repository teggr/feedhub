package dev.feedhub.app.web.site;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import dev.feedhub.app.web.admin.feeds.FeedsAdminController;
import dev.feedhub.app.web.admin.subscriptions.FeedSubscriptionsAdminController;
import dev.feedhub.app.web.feeds.FeedsController;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@ControllerAdvice
public class FeedHubNavigationControllerAdvice {

  @ModelAttribute
  public void populateModel(Model model) {

    String homeUrl = fromMethodName(FeedsController.class, "getFeeds", null, null).build().toUriString();
    model.addAttribute("homeUrl", homeUrl);

    String feedsAdminUrl =  fromMethodName(FeedsAdminController.class, "getFeeds", null, null).build().toUriString();
    model.addAttribute("feedsAdminUrl", feedsAdminUrl);

    String feedSubscriptionsAdminUrl =  fromMethodName(FeedSubscriptionsAdminController.class, "getSubscribers", null, null).build().toUriString();
    model.addAttribute("feedSubscriptionsAdminUrl", feedSubscriptionsAdminUrl);

  }

}

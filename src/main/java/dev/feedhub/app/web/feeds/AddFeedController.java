package dev.feedhub.app.web.feeds;

import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.feedhub.app.feeds.FeedConfigurations;
import dev.feedhub.app.feeds.FeedId;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/add-feed")
@RequiredArgsConstructor
public class AddFeedController {

  private final FeedConfigurations feedConfigurations;

  @PostMapping
  public Object postAddFeed(@RequestParam(value = "url", required = false) URL url,
      RedirectAttributes redirectAttributes) {

    FeedId feedId = feedConfigurations.createFeedConfiguration(url);

    // redirectAttributes.addAttribute("feedId", feedId.id());

    return "redirect:/feeds";

  }

}

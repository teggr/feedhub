package dev.feedhub.app.web.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

  @GetMapping
  public Object getHome(@AuthenticationPrincipal User user, Model model) {
    log.info("{}", user);
    return "anonymousHomeView";
  }
  
}

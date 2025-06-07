package dev.feedhub.app.web.home;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

  @GetMapping
  public String getHome(@AuthenticationPrincipal User user) {
    log.info("{}", user);
      return "redirect:/admin/feeds";
  }
  
}

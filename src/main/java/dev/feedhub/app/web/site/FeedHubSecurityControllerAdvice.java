package dev.feedhub.app.web.site;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;

@ControllerAdvice
public class FeedHubSecurityControllerAdvice {

  @ModelAttribute
  public void populateModel( @AuthenticationPrincipal User user,  Model model) {
    model.addAttribute("user", user);

    CsrfToken csrfToken = (CsrfToken) RequestContextHolder.currentRequestAttributes().getAttribute("_csrf", 0);
    model.addAttribute("_csrf", csrfToken);

  }

}

package dev.feedhub.app.web.secure;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FeedHubSecurityControllerAdvice {

  @ModelAttribute
  public void populateModel( @AuthenticationPrincipal User user, CsrfToken csrfToken, Model model ) {
    model.addAttribute("user", user);
    model.addAttribute("_csrf", csrfToken);
  }

}

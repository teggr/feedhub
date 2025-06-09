package dev.feedhub.app.web.secure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

  @GetMapping("/login")
  public String getLogin(
     @RequestParam(name = "error", required = false) String error, 
     @RequestParam(name = "logout", required = false) String logout,
      Model model ) {

      model.addAttribute("loginUrl", "/login");
      model.addAttribute("error", error);
      model.addAttribute("logout", logout);

      return "loginView";

  }
  
}

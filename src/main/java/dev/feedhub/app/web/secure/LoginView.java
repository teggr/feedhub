package dev.feedhub.app.web.secure;

import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.h3;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h1;

@Component
public class LoginView extends J2HtmlView {

  @SuppressWarnings({"unchecked", "null"})
  @Override
  protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    String loginUrl = (String) model.get("loginUrl");

    String error = (String) model.get("error");
    String logout = (String) model.get("logout");

    CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

    String extraCSS = """
      html,
      body {
        height: 100%;
      }
      
      .form-signin {
        max-width: 330px;
        padding: 1rem;
      }
      
      .form-signin .form-floating:focus-within {
        z-index: 2;
      }
      
      .form-signin input[type="email"] {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
      }
      
      .form-signin input[type="password"] {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
      }
      """;

    // build the ui
    return FeedHubSiteLayout.add("FeedHub | Login", model, Set.of(extraCSS),

      div().withClasses(h_100, d_flex, align_items_center, py_4, bg_body_tertiary).with(

        main().withClasses("form-signin", w_100, m_auto)
          .with(

            form().withMethod("post").withAction(loginUrl).with(

              span().withClasses("bi", "bi-rss-fill", mb_4),

              h1("Please Log In").withClasses(h3, mb_3, fw_normal),
              iff(error != null, div().withClasses(alert, alert_danger).withText("Invalid username and password.")),
              iff(logout != null, div().withClasses(alert, alert_danger).withText("You have been logged out.")),

              input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

              div().withClasses(form_floating).with(
                input().withType("text")
                  .withId("usernameInput")
                  .withName("username")
                  .withPlaceholder("Username")
                  .withClasses(form_control),
                label().withFor("usernameInput").withText("Username")
              ),
              div().withClasses(form_floating).with(
                input().withType("password")
                  .withId("passwordInput")
                  .withName("password")
                  .withPlaceholder("Password")
                  .withClasses(form_control),
                label().withFor("passwordInput").withText("Password")
              ),
              div().withClasses(form_check, text_start, my_3).with(
                input().withType("checkbox")
                  .withClass(form_check_input)
                  .withValue("remember-me")
                  .withId("checkDefault")
                  .withName("remember-me"),
                label().withClass(form_check_label)
                  .withFor("checkDefault")
                  .withText("Remember me")
              ),
              input().withType("submit").withValue("Log in").withClasses(btn, btn_primary, w_100, py_2)
            )

          )

      ));

  }

}


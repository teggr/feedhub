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
public class LogoutView extends J2HtmlView {

    @SuppressWarnings({"unchecked", "null"})
    @Override
    protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String logoutUrl = (String) model.get("logoutUrl");

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
        return FeedHubSiteLayout.add("FeedHub | Logout", model, Set.of(extraCSS),

                div().withClasses(h_100, d_flex, align_items_center, py_4, bg_body_tertiary).with(

                        main().withClasses("form-signin", w_100, m_auto)
                                .with(

                                        form().withMethod("post").withAction(logoutUrl).with(

                                                span().withClasses("bi", "bi-rss-fill", mb_4),

                                                h1("Are you sure you want to log out?").withClasses(h3, mb_3, fw_normal),

                                                input().withType("hidden").withName(csrfToken.getParameterName()).withValue(csrfToken.getToken()),

                                                input().withType("submit").withValue("Log Out").withClasses(btn, btn_primary, w_100, py_2)

                                        )

                                )

                ));

    }

}
package dev.feedhub.app.web.home;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static j2html.TagCreator.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;

@Component
public class AnonymousHomeView extends AbstractView {

  @Override
  @Nullable
  public String getContentType() {
    return MediaType.TEXT_HTML_VALUE;
  }

  @SuppressWarnings({ "unchecked", "null" })
  @Override
  protected void renderMergedOutputModel(@Nullable Map<String, Object> model, @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response) throws Exception {

    // get from the model
    //Page<FeedSubscription> feedSubscriptions = (Page<FeedSubscription>) model.get("feedSubscriptions");
    
   // String refreshUrl = (String) model.get("refreshUrl");
        String feedsUrl = (String) model.get("feedsUrl");
        String loginUrl = (String) model.get("loginUrl");

    // build the ui
    DomContent html = FeedHubSiteLayout.add("FeedHub | Home", model,

      each(

          FeedHubNavigation.feedHubNavigation(model),

          div().withClasses(container_fluid, py_4).with(

            div().withClasses(p_5, mb_4, bg_body_tertiary, rounded_3).with(
              div().withClasses(container_fluid, py_5).with(
                h1().withClasses(display_5, fw_bold).withText("Welcome to FeedHub"),
                p().withClasses(col_md_8, fs_4).withText("A place to manage your feeds."),
                a().withClasses(btn, btn_primary, btn_lg).withHref(feedsUrl).withText("Browse feeds")
              )
            ),

            div().withClasses(row, align_items_md_stretch).with(
              div().withClass(col_md_6).with(
                div().withClasses("h-100", p_5, text_bg_dark, rounded_3).with(
                  h2().withText("Get your feeds under control"),
                  p().withText("Easily manage your feed list and republish to other platforms whilst keeping control of your list."),
                  a().withClasses(btn, btn_outline_light).withHref(loginUrl).withText("Log In")
                )
              ),
              div().withClass(col_md_6).with(
                div().withClasses("h-100", p_5, bg_body_tertiary, border, rounded_3).with(
                  h2().withText("New feeds being added"),
                  p().withText("Look at this amazing list of recently added feeds and podcasts."),
                  a().withClasses(btn, btn_outline_secondary).withHref(feedsUrl).withText("Browse more")
                )
              )
            )

          )

      ));

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}


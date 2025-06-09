package dev.feedhub.app.web.admin.subscriptions;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.web.admin.subscriptions.components.FeedSubscriptionsAdminActionBar;
import dev.feedhub.app.web.admin.subscriptions.components.FeedSubscriptionsAdminList;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static j2html.TagCreator.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;

@Component
public class FeedSubscriptionsAdminView extends AbstractView {

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
    Page<Subscriber> feedSubscribers = (Page<Subscriber>) model.get("feedSubscribers");
    
    String refreshUrl = (String) model.get("refreshUrl");
    String addSubscriberUrl = (String) model.get("addSubscriberUrl");

    CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

    // build the ui
    DomContent html = FeedHubSiteLayout.add("FeedHub | Admin Subscriptions", model,

      each(

          FeedHubNavigation.feedHubNavigation(model),

          div().withClasses(container_fluid).with(

              FeedSubscriptionsAdminActionBar.feeSubscriptionsActionBar(csrfToken, refreshUrl, addSubscriberUrl),

              hr(),

              div().withClasses(row).with(

                div().withClasses(col).with(
                  FeedSubscriptionsAdminList.feedSubscriptions(feedSubscribers)
                )

              )

          )

      ));

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}

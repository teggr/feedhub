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

    // build the ui
    DomContent html = FeedHubSiteLayout.add("FeedHub | Home", model,

      each(

          FeedHubNavigation.feedHubNavigation(model),

          div().withClasses(container_fluid).with(

              // FeedSubscriptionsActionBar.feedSubscriptionsActionBar(refreshUrl),

              // hr(),

              // div().withClasses(row).with(

              //   div().withClasses(col).with(
              //     FeedSubscriptionsList.feedSubscriptions(feedSubscriptions)
              //   )

              // )

          )

      ));

    // output the response
    setResponseContentType(request, response);
    each(document(), html).render(IndentedHtml.into(response.getWriter()));

  }

}


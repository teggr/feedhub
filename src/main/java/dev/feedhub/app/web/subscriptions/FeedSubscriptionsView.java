package dev.feedhub.app.web.subscriptions;

import dev.feedhub.app.subscriptions.Subscription;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.feedhub.app.web.subscriptions.components.FeedSubscriptionsActionBar;
import dev.feedhub.app.web.subscriptions.components.FeedSubscriptionsList;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static dev.rebelcraft.j2html.bootstrap.Bootstrap.col;
import static j2html.TagCreator.*;

@Component
public class FeedSubscriptionsView extends J2HtmlView {

    @SuppressWarnings({"unchecked", "null"})
    @Override
    protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // get from the model
        Page<Subscription> feedSubscriptions = (Page<Subscription>) model.get("feedSubscriptions");

        String refreshUrl = (String) model.get("refreshUrl");

        // build the ui
        return FeedHubSiteLayout.add("FeedHub | Subscriptions", model,

                each(

                        FeedHubNavigation.feedHubNavigation(model),

                        div().withClasses(container_fluid).with(

                                FeedSubscriptionsActionBar.feedSubscriptionsActionBar(refreshUrl),

                                hr(),

                                div().withClasses(row).with(

                                        div().withClasses(col).with(
                                                FeedSubscriptionsList.feedSubscriptions(feedSubscriptions)
                                        )

                                )

                        )

                ));

    }

}

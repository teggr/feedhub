package dev.feedhub.app.web.feeds;

import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.feeds.FeedItem;
import dev.feedhub.app.subscriptions.Subscriber;
import dev.feedhub.app.web.feeds.components.FeedActionBar;
import dev.feedhub.app.web.feeds.components.FeedItemsList;
import dev.feedhub.app.web.site.FeedHubNavigation;
import dev.feedhub.app.web.site.FeedHubSiteLayout;
import dev.feedhub.app.web.subscriptions.SubscribeToFeedUrlBuilder;
import dev.feedhub.app.web.utils.TimeUtils;
import dev.rebelcraft.j2html.spring.webmvc.J2HtmlView;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static dev.rebelcraft.j2html.bootstrap.Bootstrap.*;
import static j2html.TagCreator.*;
import static j2html.TagCreator.h5;
import static j2html.TagCreator.small;

@Component
public class FeedView extends J2HtmlView {

    @SuppressWarnings({"unchecked", "null"})
    @Override
    protected DomContent renderMergedOutputModelDomContent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // get from the model
        Feed feed = (Feed) model.get("feed");
        Page<FeedItem> feedItems = (Page<FeedItem>) model.get("feedItems");

        String feedsUrl = (String) model.get("feedsUrl");
        SubscribeToFeedUrlBuilder subscribeToFeedUrlBuilder = (SubscribeToFeedUrlBuilder) model.get("subscribeToFeedUrlBuilder");
        String addFeedUrl = (String) model.get("addFeedUrl");

        CsrfToken csrfToken = (CsrfToken) model.get("_csrf");

        User user = (User) model.get("user");
        Subscriber subscriber = (Subscriber) model.get("subscriber");

        // build the ui
        return FeedHubSiteLayout.add("FeedHub | Feed", model,

                each(

                        FeedHubNavigation.feedHubNavigation(model),

                        FeedActionBar.feedActionBar(user, feed, feedsUrl, addFeedUrl, csrfToken),

                        hr(),

                        // TODO: summary card of the feed + expand the cards for the feed items
                        div().withClasses(container_fluid, mb_3).with(
                                div().withClasses(card).with(
                                        div().withClasses(row).with(
                                                div().withClasses(col_2).with(
                                                        img().withSrc(feed.imageUrl()).withClasses(img_fluid, img_thumbnail).withAlt("Feed icon")
                                                ),
                                                div().withClasses(col_10).with(
                                                        div().withClasses(card_body).with(
                                                                h5(feed.title()).withClasses(card_title),
                                                                iff(Optional.ofNullable(subscriber), (s) -> {
                                                                    return form().withMethod("post").withAction(subscribeToFeedUrlBuilder.build(feed.feedId())).with(
                                                                            button().withType("submit").withText("Subscribe").withClasses(btn, btn_primary));
                                                                }),
                                                                p(feed.description()).withClasses(card_text),
                                                                p().withClasses(card_text).with(
                                                                        small(TimeUtils.formatInstant(feed.publishedDate())).withClass(text_body_secondary)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ),

                        div().withClasses(container_fluid).with(

                                FeedItemsList.feeds(feedItems)

                        )

                ));

    }

}

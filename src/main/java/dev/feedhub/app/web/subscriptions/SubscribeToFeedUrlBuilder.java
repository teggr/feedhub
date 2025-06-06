package dev.feedhub.app.web.subscriptions;

import dev.feedhub.app.feeds.FeedId;

public interface SubscribeToFeedUrlBuilder {

    String build(FeedId feedId);

}

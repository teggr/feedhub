package dev.feedhub.app.web.feeds;

import dev.feedhub.app.feeds.FeedId;

public interface FeedUrlBuilder {

  String build(FeedId feedId);

}

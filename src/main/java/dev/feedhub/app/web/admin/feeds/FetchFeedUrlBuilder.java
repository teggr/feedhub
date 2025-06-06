package dev.feedhub.app.web.admin.feeds;

import dev.feedhub.app.feeds.FeedId;

public interface FetchFeedUrlBuilder {

  String build(FeedId feedId);

}

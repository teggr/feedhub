package dev.feedhub.app.subscriptions;

import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

import dev.feedhub.app.feeds.FeedId;

@Table("FEED_SUBSCRIPTIONS")
public record FeedSubscription( @Embedded(prefix = "FEED_", onEmpty = OnEmpty.USE_NULL) FeedId feedId ) {
}

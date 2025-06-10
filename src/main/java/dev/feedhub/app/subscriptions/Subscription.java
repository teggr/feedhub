package dev.feedhub.app.subscriptions;

import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

import dev.feedhub.app.feeds.FeedId;

@Table("SUBSCRIPTIONS")
public record Subscription( @Embedded(prefix = "FEED_", onEmpty = OnEmpty.USE_NULL) FeedId feedId ) {
}

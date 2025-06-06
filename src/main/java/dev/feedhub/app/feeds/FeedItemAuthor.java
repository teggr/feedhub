package dev.feedhub.app.feeds;

import org.springframework.data.relational.core.mapping.Table;

@Table("FEED_ITEM_AUTHORS")
public record FeedItemAuthor(String name, String uri, String email) {

}

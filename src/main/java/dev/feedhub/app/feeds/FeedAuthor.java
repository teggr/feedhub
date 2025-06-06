package dev.feedhub.app.feeds;

import org.springframework.data.relational.core.mapping.Table;

@Table("FEED_AUTHORS")
public record FeedAuthor(String name, String uri, String email) {

}

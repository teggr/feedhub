package dev.feedhub.app.feeds;

import org.springframework.data.relational.core.mapping.Table;

@Table("FEED_LINKS")
public record FeedLink(String href, String rel, String type, String title) {

}

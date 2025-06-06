package dev.feedhub.app.feeds;

import org.springframework.data.relational.core.mapping.Table;

@Table("FEED_ITEM_CONTENTS")
public record FeedItemContent(String type, String value) {

}

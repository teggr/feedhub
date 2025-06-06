package dev.feedhub.app.feeds;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

@Table("FEED_ITEMS")
public record FeedItem(
  @Id Long id, 
  @Embedded(prefix = "FEED_", onEmpty = OnEmpty.USE_NULL) FeedId feedId,
  Set<FeedItemAuthor> authors,
  Set<String> categories,
  Set<FeedItemContent> contents,
  String description,
  Set<FeedItemLink> links, 
  Instant publishedDate,
  String title,
  String uri,
  Instant updatedDate) {

}

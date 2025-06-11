package dev.feedhub.app.feeds;

import java.net.URL;
import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

@Table("FEEDS")
public record Feed( 
  @Id Long id, 
  @Embedded(prefix = "FEED_", onEmpty = OnEmpty.USE_NULL) FeedId feedId, 
  URL url,
  Set<FeedAuthor> authors,
  Set<String> categories,
  String description,
  String feedType,
  Set<FeedLink> links,
  Instant publishedDate,
  String title,
  String uri,
  String imageUrl) {

}

package dev.feedhub.app.feeds;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

@Table("FEED_CONFIGURATIONS")
public record FeedConfiguration(
    @Id Long id,
    @Embedded(prefix = "FEED_", onEmpty = OnEmpty.USE_NULL) FeedId feedId,
    URL url,
    @CreatedDate Instant createdDate,
    Duration schedule) {

}

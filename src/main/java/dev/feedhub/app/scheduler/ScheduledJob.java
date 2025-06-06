package dev.feedhub.app.scheduler;

import java.time.Duration;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

import dev.feedhub.app.feeds.FeedId;

@Table("SCHEDULED_JOBS")
public record ScheduledJob(
    @Id Long id,
    @Embedded(onEmpty = OnEmpty.USE_EMPTY, prefix = "FEED_") FeedId feedId,
    Instant nextScheduledRun,
    Instant lastScheduledRun,
    ScheduledRunResult lastScheduledRunResult,
    @CreatedDate Instant createdDate,
    Duration schedule) {

}

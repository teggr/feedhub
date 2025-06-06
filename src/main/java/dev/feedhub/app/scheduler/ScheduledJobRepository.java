package dev.feedhub.app.scheduler;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import dev.feedhub.app.feeds.FeedId;

public interface ScheduledJobRepository extends ListCrudRepository<ScheduledJob, Long>, ListPagingAndSortingRepository<ScheduledJob, Long> {

    List<ScheduledJob> findAllByNextScheduledRunLessThan(Instant scheduledWindow);

    ScheduledJob findByFeedId(FeedId feedId);

}

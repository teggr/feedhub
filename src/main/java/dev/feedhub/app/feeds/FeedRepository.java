package dev.feedhub.app.feeds;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface FeedRepository extends ListCrudRepository<Feed, Long>, ListPagingAndSortingRepository<Feed,Long> {

    Feed findByFeedId(FeedId feedId);

}

package dev.feedhub.app.feeds;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface FeedConfigurationRepository extends ListCrudRepository<FeedConfiguration, Long>, ListPagingAndSortingRepository<FeedConfiguration, Long> {

    FeedConfiguration findByFeedId(FeedId feedId);

}

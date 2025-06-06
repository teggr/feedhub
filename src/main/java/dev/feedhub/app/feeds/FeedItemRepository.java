package dev.feedhub.app.feeds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface FeedItemRepository extends ListCrudRepository<FeedItem, Long>, ListPagingAndSortingRepository<FeedItem, Long> {

    Page<FeedItem> findAllByFeedId(FeedId feedId, Pageable pageable);

    FeedItem findByFeedIdAndUri(FeedId feedId, String uri);

}

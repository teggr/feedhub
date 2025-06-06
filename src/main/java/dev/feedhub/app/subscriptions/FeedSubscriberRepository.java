package dev.feedhub.app.subscriptions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface FeedSubscriberRepository extends ListCrudRepository<FeedSubscriber, Long>, ListPagingAndSortingRepository<FeedSubscriber, Long> {

    FeedSubscriber findBySubscriberId(String subscriberId);

    Page<FeedSubscription> findFeedSubscriptionBySubscriberId(String subscriberId,  Pageable pageable);

}

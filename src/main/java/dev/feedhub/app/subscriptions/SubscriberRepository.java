package dev.feedhub.app.subscriptions;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface SubscriberRepository extends ListCrudRepository<Subscriber, Long>, ListPagingAndSortingRepository<Subscriber, Long> {

    Subscriber findBySubscriberId(String subscriberId);

    boolean existsByUsername(String username);

    Subscriber findByUsername(String username);

}

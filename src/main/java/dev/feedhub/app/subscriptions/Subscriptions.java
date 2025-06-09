package dev.feedhub.app.subscriptions;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import dev.feedhub.app.feeds.FeedId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Subscriptions {

  private final FeedSubscriberRepository feedSubscriberRepository;

  public Page<Subscriber> getSubscribers(Pageable pageable) {
    return feedSubscriberRepository.findAll(pageable);
  }

  public void createSubscriber(String username) {
    
    if( !feedSubscriberRepository.existsByUsername( username ) ) {

      feedSubscriberRepository.save(new Subscriber(null,username,FeedSubscriberIdGenerator.generateSubscriberId(), null, null));

    }

  }

  public void subscribeToFeed( String subscriberId, FeedId feedId) {

    Subscriber feedSubscriber = feedSubscriberRepository.findBySubscriberId(subscriberId);

    feedSubscriber.feedSubscriptions().add(new FeedSubscription(feedId));

    feedSubscriberRepository.save(feedSubscriber);

  }

  public Page<FeedSubscription> getFeedSubscriptions(String subscriberId, Pageable pageable) {
    
    Subscriber feedSubscriber = feedSubscriberRepository.findBySubscriberId(subscriberId);

    return paginateCollection( feedSubscriber.feedSubscriptions(), pageable );

  }

  private <T> Page<T> paginateCollection(Collection<T> values, Pageable pageable) {

    // get copy
    List<T> listOfValues = List.copyOf(values);

    if(pageable == null || !pageable.isPaged()) {
       return new PageImpl<>(listOfValues, pageable, listOfValues.size());
    }

    // TODO: apply sorting to the list
    Sort sort = pageable.getSort();
    if(!sort.isEmpty()) {
      sort.forEach(s -> {
        Comparator<T> comparing = Comparator.comparing(
          t -> {
            try {
              Method method = t.getClass().getMethod(s.getProperty());
              return (Comparable) method.invoke(t);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
        );
        if(s.getDirection() == Direction.ASC ) {
          comparing = comparing.reversed();
        }
        listOfValues.sort(comparing);
      });
    }

    int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), listOfValues.size());
    int last = Math.min(first + pageable.getPageSize(), listOfValues.size());

    return new PageImpl<>(listOfValues.subList(first, last), pageable, listOfValues.size());
    
  }

}

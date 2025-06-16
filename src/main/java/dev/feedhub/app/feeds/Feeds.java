package dev.feedhub.app.feeds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Feeds {

  private final FeedRepository feedRepository;
  private final FeedItemRepository feedItemRepository;

  public List<Feed> getFeeds() {
    
    return feedRepository.findAll();

  }

  public void update(Feed updatedFeed, List<FeedItem> updatedFeedItems) {

    Feed feed = feedRepository.findByFeedId(updatedFeed.feedId());
    if(feed == null) {
      feed = updatedFeed;
    } else {
      feed = new Feed(
        feed.id(),
        feed.feedId(),
        feed.url(),
        updatedFeed.authors(),
        updatedFeed.categories(),
        updatedFeed.description(),
        updatedFeed.feedType(),
        updatedFeed.links(),
        updatedFeed.publishedDate(),
        updatedFeed.title(),
        updatedFeed.uri(),
        updatedFeed.imageUrl()
      );
    }

    feed = feedRepository.save(feed);

    for( FeedItem updatedFeedItem : updatedFeedItems ) {

      FeedItem feedItem = feedItemRepository.findByFeedIdAndUri( updatedFeedItem.feedId(), updatedFeedItem.uri() );
      if(feedItem == null) {
        feedItem = updatedFeedItem;
      } else {
        feedItem = new FeedItem(
          feedItem.id(),
          feedItem.feedId(),
          updatedFeedItem.authors(),
          updatedFeedItem.categories(),
          updatedFeedItem.contents(),
          updatedFeedItem.description(),
          updatedFeedItem.links(),
          updatedFeedItem.publishedDate(),
          updatedFeedItem.title(),
          updatedFeedItem.uri(),
          updatedFeedItem.updatedDate(),
          updatedFeedItem.imageUrl()
        );
      }

      feedItem = feedItemRepository.save(feedItem);

    }

  }

  public Page<Feed> getFeeds(Pageable pageable) {
    
    return feedRepository.findAll(pageable);

  }

  public Feed getFeed(FeedId feedId) {
    return feedRepository.findByFeedId(feedId);
  }

  public Page<FeedItem> getFeedItems(FeedId feedId, Pageable pageable) {
    return feedItemRepository.findAllByFeedId(feedId, pageable);
  }

}

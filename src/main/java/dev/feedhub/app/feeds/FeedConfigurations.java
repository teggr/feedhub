package dev.feedhub.app.feeds;

import java.net.URL;
import java.time.Duration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedConfigurations {

    private final FeedConfigurationRepository feedConfigurationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Page<FeedConfiguration> getFeedConfigurations(Pageable pageable) {
        return feedConfigurationRepository.findAll(pageable);
    }

    public FeedId createFeedConfiguration(URL feedUrl) {

        try {

            // do we akready have this feed?

            // is this actually a feed url?
            SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(feedUrl));

            FeedId feedId = FeedIdGenerator.generateFeedId(); // this will be the common key for the feed

            FeedConfiguration feedConfiguration = new FeedConfiguration(null, feedId, feedUrl, null, Duration.ofDays(1));

            feedConfiguration = feedConfigurationRepository.save(feedConfiguration);

            publishFeedConfigAddedEvent(feedConfiguration);

            return feedId;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void publishFeedConfigAddedEvent(FeedConfiguration feed) {
        applicationEventPublisher.publishEvent(new FeedConfigurationAddedEvent(feed));
    }

    public FeedConfiguration updateFeedConfiguration(FeedConfiguration feed) {
        return feedConfigurationRepository.save(feed);
    }

    public FeedConfiguration getFeedConfiguration(FeedId feedId) {
      return feedConfigurationRepository.findByFeedId(feedId);
    }

}

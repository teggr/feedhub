package dev.feedhub.app.feeds;

import org.springframework.context.ApplicationEvent;

/**
 * FeedAddedEvent
 */
public class FeedConfigurationAddedEvent extends ApplicationEvent {

  public FeedConfigurationAddedEvent(FeedConfiguration feedConfiguration) {
    super(feedConfiguration);
  }

}

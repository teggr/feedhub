package dev.feedhub.app.fetch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledFetchFeedJobRunner {

  private final FetchFeedJobScheduler fetchFeedJobScheduler;

  @Scheduled(cron="0 0 * * * *") // every hour
  public void runFeedUpdateJob() {
    fetchFeedJobScheduler.runNextScheduled();
  }

}

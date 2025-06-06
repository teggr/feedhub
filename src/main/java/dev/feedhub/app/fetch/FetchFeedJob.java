package dev.feedhub.app.fetch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import dev.feedhub.app.feeds.FeedAuthor;
import dev.feedhub.app.feeds.Feed;
import dev.feedhub.app.feeds.FeedConfiguration;
import dev.feedhub.app.feeds.FeedConfigurations;
import dev.feedhub.app.feeds.FeedId;
import dev.feedhub.app.feeds.FeedItem;
import dev.feedhub.app.feeds.FeedItemAuthor;
import dev.feedhub.app.feeds.FeedItemContent;
import dev.feedhub.app.feeds.FeedItemLink;
import dev.feedhub.app.feeds.Feeds;
import dev.feedhub.app.feeds.FeedLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchFeedJob {

  private final FeedConfigurations feedConfigurations;
  private final Feeds feeds;

  public void run(FeedId feedId) {

    log.info("Refresing feed {}", feedId);

    FeedConfiguration configuration = feedConfigurations.getFeedConfiguration(feedId);

    try {

      SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(configuration.url()));

      List<SyndEntry> entries = syndFeed.getEntries();

      List<SyndLink> links = syndFeed.getLinks();

      log.info("Feed found: {} with {} enties and {} links", syndFeed.getTitle(), entries.size(), links.size());

      List<FeedItem> feedItems = new ArrayList<>();
      entries.forEach(e -> {
        feedItems.add(createFeedItem(feedId, e));
      });

      // get some backup values such as images/icons (can I get this from the website? favicon?)

      Feed feed = createFeed(feedId, configuration.url(), syndFeed);

      feeds.update(feed, feedItems);

    } catch (Exception e) {

      throw new RuntimeException(e);

    }

  }

  private FeedItem createFeedItem(FeedId feedId, SyndEntry e) {

    log.info("AUTHOR {}", e.getAuthor());
    log.info("COMMENTS {}", e.getComments());
    log.info("LINK {}", e.getLink());
    log.info("TITLE {}", e.getTitle());
    log.info("URI {}", e.getUri());
    log.info("AUTHORS {}", e.getAuthors());
    log.info("CATEGORIES {}", e.getCategories());
    log.info("CONTENTS {}", e.getContents());
    log.info("CONTRIBUTORS {}", e.getContributors());
    log.info("DESCRIPTION {}", e.getDescription());
    log.info("ENCLOSURES {}", e.getEnclosures());
    log.info("FOREGINMARKUP {}", e.getForeignMarkup());
    log.info("LINKS {}", e.getLinks());
    log.info("MODULES {}", e.getModules());
    log.info("PUBLISHEDDATE {}", e.getPublishedDate());
    log.info("TITLEEX {}", e.getTitleEx());
    log.info("UPDATEDATE {}", e.getUpdatedDate());

    return new FeedItem(null, feedId, 
      createFeedItemAuthors(e),
      e.getCategories().stream().map(c -> c.getName()).collect(Collectors.toSet()),
      createFeedItemContents(e),
      e.getDescription() != null ? e.getDescription().getValue() : null,
      createFeedItemLinks(e),
      e.getPublishedDate() != null ? e.getPublishedDate().toInstant() : null,
      e.getTitle(),
      e.getUri(),
      e.getUpdatedDate() != null ? e.getUpdatedDate().toInstant() : null
    );
  }

  private Set<FeedItemAuthor> createFeedItemAuthors(SyndEntry e) {
    
    Set<FeedItemAuthor> authors = e.getAuthors().stream().map(a -> new FeedItemAuthor(a.getName(), a.getUri(), a.getEmail())).collect(
                Collectors.toSet());
    if(e.getAuthor() != null) {
      authors.add(new FeedItemAuthor(e.getAuthor(), null, null));
    }
    return authors;

  }

  private Set<FeedItemLink> createFeedItemLinks(SyndEntry e) {

    Set<FeedItemLink> links = e.getLinks().stream()
        .map(l -> new FeedItemLink(l.getHref(), l.getRel(), l.getType(), l.getTitle())).collect(Collectors.toSet());
    if (e.getLink() != null) {
      links.add(new FeedItemLink(e.getLink(), null, null, null));
    }
    return links;

  }

  private Set<FeedItemContent> createFeedItemContents(SyndEntry e) {

    Set<FeedItemContent> links = e.getContents().stream()
        .map(c -> new FeedItemContent(c.getType(), c.getValue())).collect(Collectors.toSet());
    return links;

  }

  private Feed createFeed(FeedId feedId, URL feedUrl, SyndFeed syndFeed) {

    log.info("AUTHOR {}", syndFeed.getAuthor());
    log.info("AUTHORS {}", syndFeed.getAuthors());
    log.info("CATEGORIES {}", syndFeed.getCategories());
    log.info("CONTROBUTORS {}", syndFeed.getContributors());
    log.info("COPYRIGHT {}", syndFeed.getCopyright());
    log.info("DESCRIPTION {}", syndFeed.getDescription());
    log.info("DESCRIPTIONEX {}", syndFeed.getDescriptionEx());
    log.info("DOCS {}", syndFeed.getDocs());
    log.info("ENCODING {}", syndFeed.getEncoding());
    // log.info("{}",syndFeed.getEntries());
    log.info("FEEDTYPE {}", syndFeed.getFeedType());
    log.info("FOREIGNMARKUP {}", syndFeed.getForeignMarkup());
    log.info("GENERATOR {}", syndFeed.getGenerator());
    log.info("ICON {}", syndFeed.getIcon());
    log.info("IMAGE {}", syndFeed.getImage());
    log.info("LANGUAGE {}", syndFeed.getLanguage());
    log.info("LINK {}", syndFeed.getLink());
    log.info("LINKS {}", syndFeed.getLinks());
    log.info("MANAGINGEDITOR {}", syndFeed.getManagingEditor());
    log.info("MODULES {}", syndFeed.getModules());
    log.info("PUBLISHEDDATE {}", syndFeed.getPublishedDate());
    log.info("STYLESHEET {}", syndFeed.getStyleSheet());
    log.info("SUPPORTEDFEEDTYPES {}", syndFeed.getSupportedFeedTypes());
    log.info("TITLE {}", syndFeed.getTitle());
    log.info("TITLEEX {}", syndFeed.getTitleEx());
    log.info("URI {}", syndFeed.getUri());
    log.info("WEBMASTER {}", syndFeed.getWebMaster());

    return new Feed(null, feedId, feedUrl,
        createFeedAuthors(syndFeed),
        syndFeed.getCategories().stream().map(c -> c.getName()).collect(Collectors.toSet()), 
        syndFeed.getDescription(),
        syndFeed.getFeedType(), 
        createFeedLinks(syndFeed), syndFeed.getPublishedDate().toInstant(), 
        syndFeed.getTitle(),
        syndFeed.getUri());
  }

  private Set<FeedAuthor> createFeedAuthors(SyndFeed syndFeed) {
    
    Set<FeedAuthor> authors = syndFeed.getAuthors().stream().map(a -> new FeedAuthor(a.getName(), a.getUri(), a.getEmail())).collect(
                Collectors.toSet());
    if(syndFeed.getAuthor() != null) {
      authors.add(new FeedAuthor(syndFeed.getAuthor(), null, null));
    }
    return authors;

  }

  private Set<FeedLink> createFeedLinks(SyndFeed syndFeed) {

    Set<FeedLink> links = syndFeed.getLinks().stream()
        .map(l -> new FeedLink(l.getHref(), l.getRel(), l.getType(), l.getTitle())).collect(Collectors.toSet());
    if (syndFeed.getLink() != null) {
      links.add(new FeedLink(syndFeed.getLink(), null, null, null));
    }
    return links;

  }

}

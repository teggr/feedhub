package dev.feedhub.app.fetch;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.util.UriComponentsBuilder;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

public class FeedMediaUtils {

  @FunctionalInterface
  interface FeedImageUrlStrategy {
      String tryGetImageUrl(SyndFeed syndFeed);
  }

  // In your FetchFeedJob class:
  public static String fetchFeedImageUrl(SyndFeed syndFeed) {

    String imageUrlPlaceholder = "https://placehold.co/200x200";

    List<FeedImageUrlStrategy> strategies = List.of(
        FeedMediaUtils::getFromFeedImage,
        FeedMediaUtils::getFromMetaOgImage,
        FeedMediaUtils::getFromImageSrcLink
        // add more strategies as needed
    );

    for (FeedImageUrlStrategy strategy : strategies) {
        String url = strategy.tryGetImageUrl(syndFeed);
        if (url != null && !url.isEmpty()) {
            return url;
        }
    }

    return imageUrlPlaceholder;

  }

  // Example strategy implementations:
  private static String getFromImageSrcLink(SyndFeed syndFeed) {
      try {
          if (syndFeed.getLink() != null) {
              Document document = Jsoup.connect(syndFeed.getLink()).get();
              org.jsoup.nodes.Element imageSrcLink = document.selectFirst("head link[rel=image_src][href]");
              if (imageSrcLink != null) {
                  String href = imageSrcLink.attr("href");
                  if (href != null && !href.isEmpty()) {
                      return href;
                  }
              }
          }
      } catch (Exception ignored) {}
      return null;
  }

  private static String getFromMetaOgImage(SyndFeed syndFeed) {
      try {
          if (syndFeed.getLink() != null) {
              Document document = Jsoup.connect(syndFeed.getLink()).get();
              org.jsoup.nodes.Element ogImage = document.selectFirst("meta[property=og:image][content]");
              if (ogImage != null) {
                  String content = ogImage.attr("content");
                  if (content != null && !content.isEmpty()) {
                      return content;
                  }
              }
          }
      } catch (Exception ignored) {}
      return null;
  }

  private static String getFromFeedImage(SyndFeed syndFeed) {
      if (syndFeed.getImage() != null && syndFeed.getImage().getUrl() != null) {
          return syndFeed.getImage().getUrl();
      }
      return null;
  }

  @FunctionalInterface
  interface FeedItemImageUrlStrategy {
      String tryGetImageUrl(SyndEntry entry);
  }

  // In your FetchFeedJob class:
  public static String fetchFeedItemImageUrl(URL feedUrl, SyndEntry entry) {

    String imageUrlPlaceholder = "https://placehold.co/100x100";

    List<FeedItemImageUrlStrategy> strategies = List.of(
        //FeedMediaUtils::getFromFeedImage,
        FeedMediaUtils::getFromEntryMediaThumbnail,
        FeedMediaUtils::getFromEntryMediaContent,
        FeedMediaUtils::getFromMetaOgImage,
        FeedMediaUtils::getFromImageSrcLink
        // maybe first in content
        // add more strategies as needed
    );

    for (FeedItemImageUrlStrategy strategy : strategies) {
        String url = strategy.tryGetImageUrl(entry);
        if (url != null && !url.isEmpty()) {
          
          try{
            if(!URI.create(url).isAbsolute()) {
              url = UriComponentsBuilder.fromUri(feedUrl.toURI()).replacePath(url).build().toString();
              return url;
            } else {
              return url;
            }
          }catch(Exception e) {
            e.printStackTrace();
          }
            
        }
    }


    return imageUrlPlaceholder;

  }

  private static String getFromEntryMediaContent(SyndEntry entry) {
    try {
        Element mediaContent = entry.getForeignMarkup().stream()
          .filter(m -> m.getName().equals("content"))
          .findFirst().orElse(null);

        if (mediaContent != null) {
            Attribute attribute = mediaContent.getAttribute("url");
            if(attribute != null) {
              return attribute.getValue();
            }
        }
    } catch (Exception ignored) {}
    return null;
  }

  private static String getFromEntryMediaThumbnail(SyndEntry entry) {
    try {
        Element mediaContent = entry.getForeignMarkup().stream()
          .filter(m -> m.getName().equals("thumbnail"))
          .findFirst().orElse(null);

        if (mediaContent != null) {
            Attribute attribute = mediaContent.getAttribute("url");
            if(attribute != null) {
              return attribute.getValue();
            }
        }
    } catch (Exception ignored) {}
    return null;
  }

  private static String getFromImageSrcLink(SyndEntry entry) {
      try {
          if (entry.getLink() != null) {
              Document document = Jsoup.connect(entry.getLink()).get();
              org.jsoup.nodes.Element imageSrcLink = document.selectFirst("head link[rel=image_src][href]");
              if (imageSrcLink != null) {
                  String href = imageSrcLink.attr("href");
                  if (href != null && !href.isEmpty()) {
                      return href;
                  }
              }
          }
      } catch (Exception ignored) {}
      return null;
  }

  private static String getFromMetaOgImage(SyndEntry entry) {
      try {
          if (entry.getLink() != null) {
              Document document = Jsoup.connect(entry.getLink()).get();
              org.jsoup.nodes.Element ogImage = document.selectFirst("meta[property=og:image][content]");
              if (ogImage != null) {
                  String content = ogImage.attr("content");
                  if (content != null && !content.isEmpty()) {
                      return content;
                  }
              }
          }
      } catch (Exception ignored) {}
      return null;
  }

}

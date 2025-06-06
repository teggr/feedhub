package dev.feedhub.app.feeds;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class FeedIdGenerator {

    public static FeedId generateFeedId() {

        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());

        // URL-safe Base64 (no padding)
        String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());

        // Shorten if you want (e.g., 8 characters)
        String shortId = base64.substring(0, 8);

        return new FeedId( "feed_" + shortId );

    }

}


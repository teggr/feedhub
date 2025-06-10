package dev.feedhub.app.subscriptions;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("SUBSCRIBERS")
public record Subscriber ( @Id Long id, String username, String subscriberId, Set<Subscription> subscriptions, @CreatedDate Instant createdDate ) {


}

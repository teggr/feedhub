package dev.feedhub.app.users;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import dev.feedhub.app.subscriptions.Subscriptions;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegistrationHandler {

  private final Subscriptions subscriptions;

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent success) {

    subscriptions.createSubscriber( ((User) success.getAuthentication().getPrincipal()).getUsername() );

  }
 
}

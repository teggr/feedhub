package dev.feedhub.app.web;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebConfiguration {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        // Spring Security should completely ignore URLs starting with /resources/
        ;//.requestMatchers("/resources/**");
  }
  
  @Bean
  public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests((authorize) -> {

      authorize.requestMatchers("/").permitAll();

      authorize.requestMatchers("/admin/**").hasAnyRole("ADMIN");

      authorize.anyRequest().authenticated();

    });

    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());

    http.logout((logout) -> {
      logout.logoutSuccessUrl("/?loggedOut=true");
    });

    return http.build();

  }

}

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
      ; //.requestMatchers("/resources/**");
  }
  
  @Bean
  public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests((authorize) -> {

      authorize.requestMatchers("/", "/error", "/feeds/**").permitAll();

      authorize.requestMatchers("/admin/**", "/h2-console/**").hasAnyRole("ADMIN");

      authorize.anyRequest().authenticated();

    });

    http.formLogin(form -> {
      form.loginPage("/login").permitAll();
    });

    //http.formLogin(withDefaults());
    http.httpBasic(withDefaults());

    http.logout(logout -> {
      logout.logoutUrl("/logout").permitAll();
      logout.logoutSuccessUrl("/?logout=true");
    });

    http.csrf((csrf) -> {
      csrf.ignoringRequestMatchers("/h2-console/**");
    });

    http.headers((headers) -> {
      headers.frameOptions((frame) -> frame.sameOrigin());
    });

    return http.build();

  }

}

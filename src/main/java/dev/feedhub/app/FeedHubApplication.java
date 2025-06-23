package dev.feedhub.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FeedHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedHubApplication.class, args);
	}

}

package com.rgz.short_url_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShortUrlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlBackendApplication.class, args);
	}

}

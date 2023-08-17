package com.jema.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableJpaAuditing
public class JemaAppApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(JemaAppApplication.class);
	private static final String HEADER = "----------- Starting JemaApplication Service started  -----------";
	private static final String FOOTER = "------ ---- Stopping added JemaApplication Service-----------";

	public static void main(String[] args) {
		LOGGER.debug(HEADER);
		SpringApplication.run(JemaAppApplication.class, args);
		LOGGER.debug(FOOTER);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	}
}

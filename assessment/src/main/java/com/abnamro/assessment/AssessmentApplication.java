package com.abnamro.assessment;

import java.time.Clock;
import java.time.ZoneOffset;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
	info = @Info(
		title = "Recipes API",
		version = "0.1",
		description = "The Rest APIs to manage recipes"
	)
)
@SpringBootApplication
public class AssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Bean
	public Clock clock(){
		return Clock.system(ZoneOffset.UTC);
	}

}

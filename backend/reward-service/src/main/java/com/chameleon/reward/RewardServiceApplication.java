package com.chameleon.reward;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.chameleon")
public class RewardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner debugBeans(ApplicationContext ctx) {
		return args -> {
			System.out.println("--- BEAN CHECK ---");
			boolean exists = ctx.containsBean("rewardConsumer");
			System.out.println("Does RewardConsumer exist? " + exists);
		};
	}
}

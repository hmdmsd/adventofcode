package com.example.demo;

import com.example.demo.service.CalibrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CalibrationService calibrationService) {
		return args -> {
			System.out.println("Running tests...");
			calibrationService.runTests();

			System.out.println("\nProcessing actual input...");
			calibrationService.processCalibrationDocument("input.txt");
		};
	}
}
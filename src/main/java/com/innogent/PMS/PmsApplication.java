package com.innogent.PMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PmsApplication {

	public static void main(String[] args) {

		SpringApplication.run(PmsApplication.class, args);
		System.out.println("performance manager");
	}

}
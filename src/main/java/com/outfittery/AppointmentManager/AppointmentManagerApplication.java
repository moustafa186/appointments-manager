package com.outfittery.AppointmentManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class AppointmentManagerApplication {

	public static void main(String[] args) {
		System.out.println("Spring version: " + SpringVersion.getVersion());
		SpringApplication.run(AppointmentManagerApplication.class, args);
	}

}


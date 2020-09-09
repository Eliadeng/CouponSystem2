package com.johnbryce.CoupSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoupSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoupSysApplication.class, args);
		System.out.println("END");
	}

}

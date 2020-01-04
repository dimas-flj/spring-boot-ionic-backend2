package com.learn.cursomc;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootIonicBackendApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootIonicBackendApplication.class, args);
	}
	
	public void run(String... args) throws Exception {}
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
	}
}

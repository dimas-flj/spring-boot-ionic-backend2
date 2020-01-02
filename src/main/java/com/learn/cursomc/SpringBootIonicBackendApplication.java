package com.learn.cursomc;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootIonicBackendApplication {
	@Value("${app.email.host}")
	private String app_email_host;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootIonicBackendApplication.class, args);
	}
	
	@PostConstruct
	private void init() {
		System.out.println("APP_EMAIL_HOST = " + app_email_host);
	}
}

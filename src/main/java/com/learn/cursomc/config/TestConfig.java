package com.learn.cursomc.config;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.learn.cursomc.services.DBService;
import com.learn.cursomc.services.EmailService;
import com.learn.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		System.out.println("::::::::::::::::::::::::::::: STRATEGY = " + strategy);
		if (!"create".equals(strategy)) {
			return false;
		}
		dbService.instatiateTestDataBase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() throws IOException {
		return new MockEmailService();
	}
}
package com.learn.cursomc.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.learn.cursomc.services.EmailService;
import com.learn.cursomc.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	@Bean
	public EmailService emailService() throws IOException {
		return new SmtpEmailService();
	}
}
package com.learn.cursomc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	@Value("${app.email.protocol}")
	private String protocol;
	
	@Value("${app.email.host}")
	private String host;
	
	@Value("${app.email.port}")
	private int port;
	
	@Value("${app.email.username}")
	private String username;
	
	@Value("${app.email.password}")
	private String password;
	
	@Value("${app.email.auth}")
	private boolean auth;
	
	@Value("${app.email.starttls-enable}")
	private boolean starttlsEnable;
	
	@Value("${app.email.debug}")
	private boolean debug;
	
	@Value("${app.email.trust}")
	private String trust;
	
	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		
		javaMailSender.setHost(getHost());
		javaMailSender.setPort(getPort());
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		
		javaMailSender.setJavaMailProperties(getMailProperties());
		
		return javaMailSender;
	}
	
	private Properties getMailProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("mail.transport.protocol", getProtocol());
		properties.setProperty("mail.smtp.auth", Boolean.toString(isAuth()));
		properties.setProperty("mail.smtp.starttls.enable", Boolean.toString(isStarttlsEnable()));
		properties.setProperty("mail.debug", Boolean.toString(isDebug()));
		properties.setProperty("mail.smtp.host", getHost());
		properties.setProperty("mail.smtp.port", Integer.toString(getPort()));
		properties.setProperty("mail.smtp.ssl.trust", getTrust());
		
		return properties;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAuth() {
		return auth;
	}
	
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	
	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}
	
	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public String getTrust() {
		return trust;
	}
	
	public void setTrust(String trust) {
		this.trust = trust;
	}
}
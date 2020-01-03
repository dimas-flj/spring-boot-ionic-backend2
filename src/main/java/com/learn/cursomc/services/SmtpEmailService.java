package com.learn.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.learn.cursomc.config.MailConfig;

@Service
public class SmtpEmailService extends AbstractEmailService {
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private MailConfig mailConfig;
	
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email ...");
		LOG.info(msg.toString());
		mailSender.send(msg);
		LOG.info("Email enviado.");
	}
	
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email HTML ...");
		mailConfig.javaMailService().send(msg);
		LOG.info("Email HTML enviado.");
	};
}
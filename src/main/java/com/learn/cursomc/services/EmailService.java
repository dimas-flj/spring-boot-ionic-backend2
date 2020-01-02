package com.learn.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.domain.Pedido;

@Service
public interface EmailService {
	// VERSAO TEXTO PLANO ::::::::::::
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	void sendEmailTeste();
	// VERSAO TEXTO PLANO ::::::::::::
	
	// VERSAO HTML ::::::::::::
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	// VERSAO HTML ::::::::::::
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
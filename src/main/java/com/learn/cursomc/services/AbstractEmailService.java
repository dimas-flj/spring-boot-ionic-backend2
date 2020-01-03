package com.learn.cursomc.services;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.learn.cursomc.config.MailConfig;
import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.domain.Pedido;

@Service
public abstract class AbstractEmailService implements EmailService {
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private MailConfig mailConfig;
	
	@Value("${default.sender}")
	private String sender;
	
	public void sendEmailTeste() {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(sender);
		sm.setFrom(sender);
		sm.setSubject("Teste de envio de email via endpoint");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Envio de email somente a titulo de teste.");
		
		sendEmail(sm);
	}
	
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado: Código(" + obj.getId() + ")");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}
		catch(MessagingException | IOException e) {
			e.printStackTrace();
			
			sendOrderConfirmationEmail(obj);
		}
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException, IOException {
		MimeMessage mimeMessage = mailConfig.javaMailService().createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado: Código(" + obj.getId() + ")");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		
		return mimeMessage;
	}
	
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha.");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		
		return sm;
	}
}
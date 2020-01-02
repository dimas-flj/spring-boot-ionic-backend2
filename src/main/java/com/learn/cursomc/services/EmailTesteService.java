package com.learn.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTesteService {
	@Autowired
	private EmailService emailService;
	
	public void sendEmailTeste() {
		try {
			emailService.sendEmailTeste();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
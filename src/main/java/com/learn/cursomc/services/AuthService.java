package com.learn.cursomc.services;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.repositories.ClienteRepository;
import com.learn.cursomc.services.exceptions.ObjectNotFoundException;
import com.learn.cursomc.utils.Util;

@Service
public class AuthService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) throws IOException {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (Util.isNull(cliente)) {
			throw new ObjectNotFoundException("Email não encontrado.");
		}
		
		String newPass = getNewPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}
	
	private String getNewPassword() {
		char[] vet = new char[10];
		for(int i = 0; i < 10; i ++) {
			vet[i] = getRandomChar();
		}
		return new String(vet);
	}
	
	private char getRandomChar() {
		int opt = random.nextInt(3);
		switch(opt) {
			case 0 : { // gera um digito
				return (char) (random.nextInt(10) + 48);
			}
			case 1 : { // gera uma letra maiuscula
				return (char) (random.nextInt(26) + 65);
			}
			default : { // gera uma letra minuscula
				return (char) (random.nextInt(26) + 97);
			}
		}
	}
}
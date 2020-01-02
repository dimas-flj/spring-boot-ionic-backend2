package com.learn.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.cursomc.services.EmailTesteService;

@RestController
@RequestMapping(value="/email_teste")
public class EnvioEmailTesteResource {
	@Autowired
	private EmailTesteService es;
	
	@RequestMapping(method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> send() {
		es.sendEmailTeste();
		return ResponseEntity.noContent().build();
	}
}
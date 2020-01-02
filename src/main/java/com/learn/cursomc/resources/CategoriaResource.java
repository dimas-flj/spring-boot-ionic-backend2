package com.learn.cursomc.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	@Value("${app.email.username}")
	private String app_email_username;
	
	@RequestMapping(method = RequestMethod.GET)
	public String listar() {
		return "REST está funcionando! <br/> placeHolder (app_email_username) = " + app_email_username;
	}
}
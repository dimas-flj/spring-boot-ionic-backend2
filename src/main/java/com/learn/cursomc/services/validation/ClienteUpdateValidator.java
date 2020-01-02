package com.learn.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.dto.ClienteDTO;
import com.learn.cursomc.repositories.ClienteRepository;
import com.learn.cursomc.resources.exceptions.FieldMessage;
import com.learn.cursomc.utils.Util;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	public void initialize(ClienteUpdate ann) {}
	
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (!Util.isNull(aux) && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente."));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
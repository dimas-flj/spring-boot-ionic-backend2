package com.learn.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.repositories.ClienteRepository;
import com.learn.cursomc.security.UserSS;
import com.learn.cursomc.utils.Util;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private ClienteRepository repo;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		if (Util.isNull(cli)) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
}
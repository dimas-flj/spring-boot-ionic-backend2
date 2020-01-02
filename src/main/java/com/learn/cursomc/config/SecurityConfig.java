package com.learn.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.learn.cursomc.security.JWTAuthenticationFilter;
import com.learn.cursomc.security.JWTAuthorizationFilter;
import com.learn.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public static final String[] PUBLIC_MATCHES = {
		"/h2-console/**"
	};
	
	public static final String[] PUBLIC_MATCHES_GET = {
		"/produtos/**", 
		"/categorias/**", 
		"/estados/**", 
		"/email_teste/**"
	};
	
	public static final String[] PUBLIC_MATCHES_POST = {
		"/clientes", 
		"/auth/forgot/**"
	};
	
	protected void configure(HttpSecurity http) throws Exception {
		// configuracao para peculiaridade do DB em memoria H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		// ativa o metodo abaixo corsConfigurationSource()
		http.cors().
		// desabilita protecao CSRF em sistemas stateless
		and().csrf().disable();
		
		http.
		// autoriza todos os caminhos do vetor PUBLIC_MATCHES
		authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHES_POST).permitAll().
		antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll().
		antMatchers(PUBLIC_MATCHES).permitAll().
		
		// exige autenticacao para caminhos nao existentes no vetor PUBLIC_MATCHES
		anyRequest().authenticated();
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		// assegura que esse back-ende nao crie sessao de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
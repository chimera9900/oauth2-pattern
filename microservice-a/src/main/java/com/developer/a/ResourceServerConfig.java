package com.developer.a;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests(authorizeRequests ->
		authorizeRequests
		.mvcMatchers("/service-a/**").access("hasAuthority('SCOPE_authority-a')")
		.anyRequest().authenticated()
				)
		.oauth2ResourceServer()
		.jwt();
	}

}

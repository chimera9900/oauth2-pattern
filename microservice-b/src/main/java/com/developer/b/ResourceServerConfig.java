package com.developer.b;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
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
		.oauth2ResourceServer(oauth2ResourceServer -> 
		oauth2ResourceServer.jwt()
				)
		.oauth2Client();
		
		;
	}

}
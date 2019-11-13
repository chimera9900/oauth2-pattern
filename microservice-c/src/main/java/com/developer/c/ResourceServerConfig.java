package com.developer.c;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.mvcMatchers("/service-c/**").access("hasAuthority('SCOPE_authority-c')")
					.anyRequest().authenticated())
			.oauth2ResourceServer()
				.jwt();
	}
	// @formatter:on
}
package com.developer.ui;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.anyRequest().authenticated())
			.oauth2Login(oauth2Login ->
				oauth2Login
					.loginPage("/oauth2/authorization/login-client")
					.failureUrl("/login?error")
					.permitAll())
			.logout(logout ->
				logout
					.logoutSuccessUrl("http://localhost:8080/uaa/logout.do?client_id=login-client&redirect=http://localhost:7080"))
			.oauth2Client();
	}

}

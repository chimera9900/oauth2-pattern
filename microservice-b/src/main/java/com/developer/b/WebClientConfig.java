package com.developer.b;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.JwtBearerOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauthConfig = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
		return WebClient.builder()
				.apply(oauthConfig.oauth2Configuration())
				.build()
				;
	}
	
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository
			) {
		
		DefaultOAuth2AuthorizedClientManager authorizedClientManager = 
				new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
		
		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
		.clientCredentials()
		.provider(new JwtBearerOAuth2AuthorizedClientProvider())
		.build();
		
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
		authorizedClientManager.setContextAttributesMapper(contextAttributesMapper());
		return authorizedClientManager;
		
	}
	
	private Function<OAuth2AuthorizeRequest, Map<String,Object>> contextAttributesMapper(){
		return authorizeRequest -> {
			Map<String, Object> contextAttributes = Collections.EMPTY_MAP;
			if(authorizeRequest.getPrincipal() instanceof JwtAuthenticationToken) {
				contextAttributes = new HashMap<>();
				contextAttributes.put(JwtBearerOAuth2AuthorizedClientProvider.JWT_ATTRIBUTE_NAME,
						((JwtAuthenticationToken) authorizeRequest.getPrincipal()).getToken() );
			}
			return contextAttributes;
		};
	}

}

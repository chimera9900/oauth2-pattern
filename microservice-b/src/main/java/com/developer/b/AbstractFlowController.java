package com.developer.b;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClient;

import com.developer.b.ServicesConfig.ServiceConfig;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

abstract class AbstractFlowController {

	private static final String SERVICE_B = "service_b";
	private final WebClient webClient;
	private final ServicesConfig servicesConfig;

	public AbstractFlowController(WebClient webClient, ServicesConfig servicesConfig) {
		this.webClient = webClient;
		this.servicesConfig = servicesConfig;
	}

	public ServiceCallResponse callService(Jwt jwt) {
		ServiceConfig config = servicesConfig.getConfig(ServicesConfig.SERVICE_C);
		return webClient.get().uri(config.getUri()).headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
				.retrieve().bodyToMono(ServiceCallResponse.class).block();
	}
	
	public ServiceCallResponse callService(String clientRegistrationId) {
		ServiceConfig config = servicesConfig.getConfig(ServicesConfig.SERVICE_C);
		return webClient
				.get()
				.uri(config.getUri())
				.attributes(clientRegistrationId(clientRegistrationId))
				.retrieve()
				.bodyToMono(ServiceCallResponse.class)
				.block()
				;
	}

	protected ServiceCallResponse fromServiceB(JwtAuthenticationToken jwtAuthentication, HttpServletRequest request,
			ServiceCallResponse... serviceCallResponses) {

		ServiceCallResponse serviceCallResponse = new ServiceCallResponse();
		serviceCallResponse.setServiceName(SERVICE_B);
		serviceCallResponse.setServiceUri(request.getRequestURL().toString());
		serviceCallResponse.setJti(jwtAuthentication.getToken().getId());
		serviceCallResponse.setSub(jwtAuthentication.getToken().getSubject());
		serviceCallResponse.setAud(jwtAuthentication.getToken().getAudience());
		serviceCallResponse.setAuthorities(jwtAuthentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));
		if (serviceCallResponses != null) {
			serviceCallResponse.setServiceCallResponses(Arrays.asList(serviceCallResponses));
		}

		return serviceCallResponse;
	}

}

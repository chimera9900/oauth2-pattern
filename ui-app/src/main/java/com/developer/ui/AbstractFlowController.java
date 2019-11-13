package com.developer.ui;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient
;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.developer.ui.ServicesConfig.ServiceConfig;



abstract class AbstractFlowController {
	protected static final String FLOW_TYPE_PARAMETER = "flowType";
	private final WebClient webClient;
	private final ServicesConfig servicesConfig;

	protected AbstractFlowController(WebClient webClient, ServicesConfig servicesConfig) {
		this.webClient = webClient;
		this.servicesConfig = servicesConfig;
	}

	protected ServiceCallResponse callService(String serviceId,
												OAuth2AuthorizedClient authorizedClient) {

		return callService(serviceId, authorizedClient, new LinkedMultiValueMap<>());
	}

	protected ServiceCallResponse callService(String serviceId,
												OAuth2AuthorizedClient authorizedClient,
												MultiValueMap<String, String> params) {

		ServicesConfig.ServiceConfig serviceConfig = this.servicesConfig.getConfig(serviceId);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(serviceConfig.getUri());
		if (!params.isEmpty()) {
			uriBuilder.queryParams(params);
		}
		URI uri = uriBuilder.build().toUri();

		return this.webClient
				.get()
				.uri(uri)
				.attributes(oauth2AuthorizedClient(authorizedClient))
				.retrieve()
				.bodyToMono(ServiceCallResponse.class)
				.block();
	}

	protected ServiceCallResponse fromUiApp(OAuth2AuthenticationToken oauth2Authentication,
											HttpServletRequest request,
											ServiceCallResponse... serviceCallResponses) {

		OidcUser oidcUser = (OidcUser) oauth2Authentication.getPrincipal();

		ServiceCallResponse serviceCallResponse = new ServiceCallResponse();
		serviceCallResponse.setServiceName(ServicesConfig.UI_APP);
		serviceCallResponse.setServiceUri(request.getRequestURL().toString());
		serviceCallResponse.setJti("(opaque to client)");
		serviceCallResponse.setSub(oidcUser.getSubject());
		serviceCallResponse.setAud(oidcUser.getAudience());
		serviceCallResponse.setAuthorities(oauth2Authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));
		if (serviceCallResponses != null) {
			serviceCallResponse.setServiceCallResponses(Arrays.asList(serviceCallResponses));
		}

		return serviceCallResponse;
	}
}
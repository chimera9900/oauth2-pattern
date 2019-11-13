package com.developer.b;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "/service-b", params = {"flowType=token_relay"})
public class ServiceBTokenRelayController extends AbstractFlowController {

	public ServiceBTokenRelayController(WebClient webClient, ServicesConfig servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	public ServiceCallResponse serviceB_TokenRelay(JwtAuthenticationToken jwtAuthentication,
													HttpServletRequest request) {

		ServiceCallResponse serviceCCallResponse = callService(jwtAuthentication.getToken());
		return fromServiceB(jwtAuthentication, request, serviceCCallResponse);
	}
}
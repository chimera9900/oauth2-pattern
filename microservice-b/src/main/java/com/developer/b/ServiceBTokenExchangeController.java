package com.developer.b;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "/service-b", params = {"flowType=token_exchange"})
public class ServiceBTokenExchangeController extends AbstractFlowController {

	public ServiceBTokenExchangeController(WebClient webClient, ServicesConfig servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	public ServiceCallResponse serviceB_TokenExchange(JwtAuthenticationToken jwtAuthentication,
														HttpServletRequest request) {

		ServiceCallResponse serviceCCallResponse = callService("client-c-exchange");
		return fromServiceB(jwtAuthentication, request, serviceCCallResponse);
	}
}
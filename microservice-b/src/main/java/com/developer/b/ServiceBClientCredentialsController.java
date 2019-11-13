package com.developer.b;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "/service-b", params = {"flowType=client_credentials"})
public class ServiceBClientCredentialsController extends AbstractFlowController {

	public ServiceBClientCredentialsController(WebClient webClient, ServicesConfig servicesConfig) {
		super(webClient, servicesConfig);
	}
	
	@GetMapping
	public ServiceCallResponse serviceB_Credential(JwtAuthenticationToken token,
			HttpServletRequest request) {
		ServiceCallResponse serviceCCallResponse = callService("client-c");
		return fromServiceB(token, request, serviceCCallResponse);
	}

}

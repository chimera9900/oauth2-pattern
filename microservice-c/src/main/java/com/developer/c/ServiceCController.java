package com.developer.c;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-c")
public class ServiceCController {
	private static final String SERVICE_C = "service-c";

	@GetMapping
	public ServiceCallResponse serviceC(JwtAuthenticationToken jwtAuthentication,
										HttpServletRequest request) {
		ServiceCallResponse serviceCallResponse = new ServiceCallResponse();
		serviceCallResponse.setServiceName(SERVICE_C);
		serviceCallResponse.setServiceUri(request.getRequestURL().toString());
		serviceCallResponse.setJti(jwtAuthentication.getToken().getId());
		serviceCallResponse.setSub(jwtAuthentication.getToken().getSubject());
		serviceCallResponse.setAud(jwtAuthentication.getToken().getAudience());
		serviceCallResponse.setAuthorities(jwtAuthentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));

		return serviceCallResponse;
	}
}


package com.developer.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/flow-ab")
public class FlowABController extends AbstractFlowController {
	

	public FlowABController(WebClient webClient, ServicesConfig servicesConfig) {
		super(webClient, servicesConfig);
	}
	
	@GetMapping
	public String flowAB(@RegisteredOAuth2AuthorizedClient("client-ab") OAuth2AuthorizedClient clientAB,
			OAuth2AuthenticationToken token,
			HttpServletRequest request,
			Map<String,Object> model
			) {
		
		ServiceCallResponse serviceACallResponse = callService(ServicesConfig.SERVICE_A, clientAB);
		ServiceCallResponse serviceBCallResponse = callService(ServicesConfig.SERVICE_B, clientAB);
		
		model.put("flowABCall", fromUiApp(token, request, serviceACallResponse, serviceBCallResponse));
//		model.put("flowABCall", fromUiApp(token, request, serviceACallResponse));
		model.put("flowActive", true);

		return "index";
		
	}
	
	

}

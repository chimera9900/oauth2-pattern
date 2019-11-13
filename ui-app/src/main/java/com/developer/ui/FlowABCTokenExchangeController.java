package com.developer.ui;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping(path = "/flow-abc", params = {"flowType=token_exchange"})
public class FlowABCTokenExchangeController extends AbstractFlowController {
	private static final String FLOW_TYPE_TOKEN_EXCHANGE = "token_exchange";

	public FlowABCTokenExchangeController(WebClient webClient, ServicesConfig servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	public String flowABC_TokenExchange(@RegisteredOAuth2AuthorizedClient("client-ab") OAuth2AuthorizedClient clientAB,
										OAuth2AuthenticationToken oauth2Authentication,
										HttpServletRequest request,
										Map<String, Object> model) {

		ServiceCallResponse serviceACallResponse = callService(ServicesConfig.SERVICE_A, clientAB);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put(FLOW_TYPE_PARAMETER, Collections.singletonList(FLOW_TYPE_TOKEN_EXCHANGE));
		ServiceCallResponse serviceBCallResponse = callService(ServicesConfig.SERVICE_B, clientAB, params);

		String modelAttr = "flowABCCall_" + FLOW_TYPE_TOKEN_EXCHANGE;
		model.put(modelAttr, fromUiApp(oauth2Authentication, request, serviceACallResponse, serviceBCallResponse));
		model.put("flowActive", true);

		return "index";
	}
}
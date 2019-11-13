package com.developer.ui;

import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class DefaultController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping(path = "/login", params = "error")
	public String loginError(@SessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) AuthenticationException authEx,
												Map<String, Object> model) {
		String errorMessage = authEx != null ? authEx.getMessage() : "[unknown error]";
		model.put("errorMessage", errorMessage);
		return "error";
	}

	@GetMapping("/session-state")
	public String sessionState() {
		return "session-state";
	}

}

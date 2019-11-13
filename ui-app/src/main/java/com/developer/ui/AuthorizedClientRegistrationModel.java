package com.developer.ui;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

public class AuthorizedClientRegistrationModel {
	
	private final ClientRegistration clientRegistration;
	private final OAuth2AuthorizedClient authorizedClient;

	public AuthorizedClientRegistrationModel(ClientRegistration clientRegistration,
												OAuth2AuthorizedClient authorizedClient) {
		this.clientRegistration = clientRegistration;
		this.authorizedClient = authorizedClient;
	}

	public String getClientId() {
		return getClientRegistration().getClientId();
	}

	public String getAuthorizationGrantType() {
		return getClientRegistration().getAuthorizationGrantType().getValue();
	}

	public boolean isAuthorized() {
		return getAuthorizedClient() != null;
	}

	public Set<String> getAuthorizedScopes() {
		return isAuthorized() ?
				new TreeSet<>(getAuthorizedClient().getAccessToken().getScopes()) :
				Collections.emptySet();
	}

	ClientRegistration getClientRegistration() {
		return this.clientRegistration;
	}

	OAuth2AuthorizedClient getAuthorizedClient() {
		return this.authorizedClient;
	}
}

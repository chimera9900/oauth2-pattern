package com.developer.ui;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@ConfigurationProperties("oauth2.resource")
public class ServicesConfig {
	public static final String UI_APP = "ui-app";
	public static final String SERVICE_A = "service-a";
	public static final String SERVICE_B = "service-b";
	private Map<String, ServiceConfig> services;

	public Map<String, ServiceConfig> getServices() {
		return this.services;
	}

	public void setServices(Map<String, ServiceConfig> services) {
		this.services = services;
	}

	public ServiceConfig getConfig(String serviceId) {
		return this.getServices().entrySet().stream()
				.filter(e -> e.getKey().equalsIgnoreCase(serviceId))
				.findFirst()
				.map(Map.Entry::getValue)
				.orElse(null);
	}

	public static class ServiceConfig {
		private String uri;

		public String getUri() {
			return this.uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
	}
}
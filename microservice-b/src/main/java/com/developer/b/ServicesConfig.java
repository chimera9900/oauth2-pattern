package com.developer.b;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oauth2.resource")
public class ServicesConfig {
	
	public static final String SERVICE_C = "service-c";
	public Map<String, ServiceConfig> services;
	
	public ServiceConfig getConfig(String ServiceId) {
		return getServices().entrySet()
				.stream()
				.filter(e -> e.getKey().equalsIgnoreCase(ServiceId))
				.findFirst()
				.map(Map.Entry::getValue)
				.orElse(null)
				;
	}
	
	public Map<String, ServiceConfig> getServices() {
		return services;
	}

	public void setServices(Map<String, ServiceConfig> services) {
		this.services = services;
	}

	public static class ServiceConfig{
		private String uri;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
		
	}
	

}

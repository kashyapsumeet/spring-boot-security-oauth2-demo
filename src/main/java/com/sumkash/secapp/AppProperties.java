package com.sumkash.secapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sbs")
public class AppProperties {

	private final Github github = new Github();

	private final Security security = new Security();

	public Github getGithub() {
		return this.github;
	}

	public Security getSecurity() {
		return this.security;
	}


	public static class Github {
		
		private String token;

		public String getToken() {
			return this.token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

	public static class Security {
		
		private List<String> admins = new ArrayList<>();
		
		public List<String> getAdmins() {
			return admins;
		}

		public void setAdmins(List<String> admins) {
			this.admins = admins;
		}

	}

}

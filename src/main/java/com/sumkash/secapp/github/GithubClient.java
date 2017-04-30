package com.sumkash.secapp.github;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sumkash.secapp.AppProperties;

@Service
public class GithubClient {
	
	private final RestTemplate restTemplate;

	public GithubClient(RestTemplateBuilder restTemplateBuilder,
			AppProperties properties) {
		this.restTemplate = restTemplateBuilder.additionalCustomizers(rt ->
				rt.getInterceptors().add(new GithubAppTokenInterceptor(properties.getGithub().getToken()))).build();
	}
	
	public GithubUser getUser(String githubId) {
		return invoke(createRequestEntity(
				String.format("https://api.github.com/users/%s", githubId)), GithubUser.class).getBody();
	}

	private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
		try {
			return this.restTemplate.exchange(request, type);
		}
		catch (RestClientException ex) {
			throw ex;
		}
	}

	private RequestEntity<?> createRequestEntity(String url) {
		try {
			return RequestEntity.get(new URI(url))
					.accept(MediaType.APPLICATION_JSON).build();
		}
		catch (URISyntaxException ex) {
			throw new IllegalStateException("Invalid URL " + url, ex);
		}
	}
	
	private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

		private final String token;

		GithubAppTokenInterceptor(String token) {
			this.token = token;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
				ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
			if (StringUtils.hasText(this.token)) {
				byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
				httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString(basicAuthValue));
			}
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}

	}

}

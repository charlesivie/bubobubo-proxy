package uk.co.bubobubo.service;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Service
public class SesameProxyService {

	@Value("${proxy.sesame.url}")
	private String sesameUrl;

	public void flushSesameResponse(
			final String path,
			final HttpMethod method,
			final Map<String, String> parameters,
			final HttpHeaders headers,
			final Resource resource,
			final HttpServletResponse response
	) throws IOException {

		UriBuilder uriBuilder = UriBuilder.fromUri(sesameUrl + path);

		for (Map.Entry<String, String> param : parameters.entrySet()) {
			uriBuilder.queryParam(param.getKey(), UriUtils.encodeQuery(param.getValue(), "UTF-8"));
		}

		URI uri = uriBuilder.build();

		RestTemplate restTemplate;
		if (uri.getUserInfo() != null && !uri.getUserInfo().equalsIgnoreCase("")) {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
					uri.getUserInfo().split(":")[0],
					uri.getUserInfo().split(":")[1]
			));
			httpClient.setCredentialsProvider(credentialsProvider);
			HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
		} else {
			restTemplate = new RestTemplate();
		}


		ResponseEntity<String> responseEntity;
		if (resource != null && resource.exists() && resource.contentLength() > 0) {
			responseEntity = restTemplate.exchange(
					uri,
					method,
					new HttpEntity<Resource>(resource, headers),
					String.class
			);
		} else {

			responseEntity = restTemplate.exchange(
					uri,
					method,
					new HttpEntity<Resource>(headers),
					String.class
			);
		}


		if (headers != null) {
			for (String key : responseEntity.getHeaders().keySet()) {
				response.addHeader(key, responseEntity.getHeaders().toSingleValueMap().get(key));
			}
		}

		if (responseEntity.hasBody()) {
			response.getOutputStream().write(responseEntity.getBody().getBytes());
		}
		response.setStatus(responseEntity.getStatusCode().value());
		response.flushBuffer();

	}

}

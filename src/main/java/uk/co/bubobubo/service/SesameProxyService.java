package uk.co.bubobubo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.Collections;
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

		RestTemplate restTemplate = new RestTemplate();

		UriBuilder uri = UriBuilder.fromUri(sesameUrl + path);

		for (Map.Entry<String, String> param : parameters.entrySet()) {
			uri.queryParam(param.getKey(), UriUtils.encodeQuery(param.getValue(), "UTF-8"));
		}

		ResponseEntity<String> responseEntity;
		if (resource != null && resource.exists() && resource.contentLength() > 0) {
			responseEntity = restTemplate.exchange(
					uri.build(),
					method,
					new HttpEntity<Resource>(resource, headers),
					String.class
			);
		} else {

			responseEntity = restTemplate.exchange(
					uri.build(),
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

	public void flushSesameResponse(String path, HttpMethod method, HttpServletResponse response) throws IOException {
		flushSesameResponse(path, method, Collections.<String, String>emptyMap(), null, null, response);
	}


	@ExceptionHandler(HttpStatusCodeException.class)
	public
	@ResponseBody
	String handleException(HttpStatusCodeException exception, HttpServletResponse response) throws IOException {

		response.setStatus(exception.getStatusCode().value());

		for (Map.Entry<String, String> entry : exception.getResponseHeaders().toSingleValueMap().entrySet()) {
			response.addHeader(entry.getKey(), entry.getValue());
		}

		return exception.getResponseBodyAsString();
	}

}

package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import uk.co.bubobubo.service.SesameProxyService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
@RequestMapping("/repositories")
public class RepoController {

	@Autowired
	private SesameProxyService proxyService;

	@RequestMapping(value="/{repoId}", method = RequestMethod.GET)
	public void proxyGetRequest(
			@PathVariable("repoId") String repoId,
			@RequestParam Map<String, String> parameters,
			@RequestHeader HttpHeaders headers,
			HttpServletResponse response
	) throws URISyntaxException, IOException {

		proxyService.flushSesameResponse("/repositories/" + repoId, HttpMethod.GET, parameters, headers, response);

	}

	@ExceptionHandler(HttpStatusCodeException.class)
	public @ResponseBody String handleException(HttpStatusCodeException exception, HttpServletResponse response) throws IOException {

		response.setStatus(exception.getStatusCode().value());

		for(Map.Entry<String, String> entry:exception.getResponseHeaders().toSingleValueMap().entrySet()){
			response.addHeader(entry.getKey(), entry.getValue());
		}

		return exception.getResponseBodyAsString();
	}


}

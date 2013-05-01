package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import uk.co.bubobubo.service.SesameProxyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
public class RepoController {

	@Autowired
	private SesameProxyService proxyService;

	@RequestMapping(value="/**")
	public void repoQueryWithId(
			@RequestParam Map<String, String> parameters,
			@RequestHeader HttpHeaders headers,
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestBody Resource resource
	) throws URISyntaxException, IOException {

		proxyService.flushSesameResponse(
				request.getPathInfo(),
				HttpMethod.valueOf(request.getMethod().toUpperCase()),
				parameters,
				headers,
				resource,
				response
		);

	}

    @RequestMapping(value="/repositories/{repoId}", method = RequestMethod.DELETE)
    public void handleDelete(HttpServletResponse response) throws IOException {
        response.sendError(403, "Remove repositories through the control panel at sparqlr.com");
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

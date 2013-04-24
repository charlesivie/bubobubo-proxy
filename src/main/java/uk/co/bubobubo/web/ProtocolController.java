package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import uk.co.bubobubo.service.SesameProxyService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/protocol")
public class ProtocolController {

	@Autowired
	private SesameProxyService proxyService;

	@RequestMapping(method = RequestMethod.GET)
	public void getProtocol(HttpServletResponse response) throws IOException {
		proxyService.flushSesameResponse("/protocol", HttpMethod.GET, response);
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

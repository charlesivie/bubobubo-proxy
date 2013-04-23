package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.bubobubo.service.SesameProxyService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/protocol")
public class ProtocolController {

	@Autowired
	private SesameProxyService proxyService;

	@RequestMapping(method = RequestMethod.GET)
	public void getProtocol(HttpServletResponse response) throws IOException {
		proxyService.flushSesameResponse("/protocol", HttpMethod.GET, response);
	}


}

package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.co.bubobubo.service.SesameProxyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
@RequestMapping("/repositories")
public class StatementsController {

    @Autowired
    private SesameProxyService proxyService;

    @RequestMapping("/{repoId}/statements")
    public void proxyGetRequest(
            @PathVariable("repoId") String repoId,
            @RequestParam Map<String, String> parameters,
            @RequestHeader HttpHeaders headers,
            HttpServletResponse response,
            HttpServletRequest request,
			@RequestBody Resource resource
    ) throws URISyntaxException, IOException {

        proxyService.flushSesameResponse(
                "/repositories/" + repoId + "/statements",
                HttpMethod.valueOf(request.getMethod().toUpperCase()),
                parameters,
                headers,
				resource,
                response
        );

    }

}

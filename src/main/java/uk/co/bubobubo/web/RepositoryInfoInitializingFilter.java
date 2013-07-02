package uk.co.bubobubo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.bubobubo.service.RepositoryService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static uk.co.bubobubo.service.RepositoryIdExtractor.extractRepositoryId;

@Component
public class RepositoryInfoInitializingFilter implements Filter {

	@Autowired
	private RepositoryService repositoryService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String repositoryId = extractRepositoryId(((HttpServletRequest) servletRequest).getPathInfo());

		servletRequest.setAttribute("repoInfo", repositoryService.getRepositoryInfo(repositoryId));
		servletRequest.setAttribute("repositoryId", repositoryId);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

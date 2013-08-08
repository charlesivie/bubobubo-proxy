package uk.co.bubobubo.web;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static uk.co.bubobubo.service.RepositoryIdExtractor.extractRepositoryId;

@Component
public class RepositoryInfoInitializingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String repositoryId = extractRepositoryId(((HttpServletRequest) servletRequest).getPathInfo());

		servletRequest.setAttribute("repositoryId", repositoryId);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}

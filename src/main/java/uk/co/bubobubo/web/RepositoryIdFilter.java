package uk.co.bubobubo.web;

import uk.co.bubobubo.service.RepositoryIdExtractor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RepositoryIdFilter implements Filter {

    private RepositoryIdExtractor repositoryIdExtractor = new RepositoryIdExtractor();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String repositoryId =
                repositoryIdExtractor.extractRepositoryId(((HttpServletRequest)servletRequest).getPathInfo());
        servletRequest.setAttribute("repositoryId", repositoryId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

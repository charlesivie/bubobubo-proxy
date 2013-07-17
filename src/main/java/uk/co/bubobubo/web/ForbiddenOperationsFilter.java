package uk.co.bubobubo.web;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ForbiddenOperationsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

		String repositoryId = (String) servletRequest.getAttribute("repositoryId");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String pathInfo = httpServletRequest.getPathInfo();

        if (httpServletRequest.getMethod().equalsIgnoreCase("DELETE")
                && pathInfo.equalsIgnoreCase("/repositories/" + repositoryId)) {
            httpServletResponse.setStatus(403);
            httpServletResponse.flushBuffer();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

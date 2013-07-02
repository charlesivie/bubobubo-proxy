package uk.co.bubobubo.web;

import org.springframework.stereotype.Component;
import uk.co.bubobubo.domain.RepoInfo;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RepositoryLimitFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		RepoInfo repoInfo = (RepoInfo) servletRequest.getAttribute("repoInfo");

		if (repoInfo.isOverLimit()) {
			httpServletResponse.setStatus(403);
			httpServletResponse.getWriter().write(
					String.valueOf(
							repoInfo.getLevel())
							+ " repo's are limited to "
							+ repoInfo.getLimit()
							+ " triples. logon to Sparqlr.com to upgrade"
			);
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

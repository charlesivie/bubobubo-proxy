package uk.co.bubobubo.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.co.bubobubo.security.RepositoryRoleHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collection;

public class RoleExtractionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /* no need to do anything? */
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        RepositoryRoleHolder holder = new RepositoryRoleHolder();
        for(GrantedAuthority grantedAuthority : roles) {

            holder.addRoleName(grantedAuthority.getAuthority());
        }

        servletRequest.setAttribute("repositories", holder);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

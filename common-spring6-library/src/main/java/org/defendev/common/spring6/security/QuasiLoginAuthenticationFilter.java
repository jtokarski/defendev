package org.defendev.common.spring6.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.List;




public class QuasiLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LogManager.getLogger();

    public QuasiLoginAuthenticationFilter(String defaultFilterProcessesUrl,
                                          AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
        final String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            throw new QuasiLoginAuthenticationException("Authentication not supported for HTTP method: " +
                requestMethod);
        }

        final String quasiUsername = request.getParameter("quasiUsername");
        final String quasiFullName = request.getParameter("quasiFullName");

        final UserDetails quasiUser = User.builder()
            .username(quasiUsername)
            .password("quasi")
            .accountExpired(false)
            .accountLocked(false)
            .authorities(List.<GrantedAuthority>of())
            .build();

        final QuasiLoginAuthenticationToken token = new QuasiLoginAuthenticationToken(quasiUser, List.of(), false);

        return this.getAuthenticationManager().authenticate(token);
    }

}

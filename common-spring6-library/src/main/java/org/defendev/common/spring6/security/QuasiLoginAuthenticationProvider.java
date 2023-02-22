package org.defendev.common.spring6.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;



public class QuasiLoginAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LogManager.getLogger();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(QuasiLoginAuthenticationToken.class, authentication,
            () -> "QuasiLoginAuthenticationProvider only supports QuasiLoginAuthenticationToken");
        final QuasiLoginAuthenticationToken token = (QuasiLoginAuthenticationToken) authentication;
        if (isNull(token.getPrincipal()) || isBlank(token.getName())) {
            throw new QuasiLoginAuthenticationException("Something went wrong during QuasiLogin authentication");
        }
        return token.authenticated();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (QuasiLoginAuthenticationToken.class.isAssignableFrom(authentication));
    }

}

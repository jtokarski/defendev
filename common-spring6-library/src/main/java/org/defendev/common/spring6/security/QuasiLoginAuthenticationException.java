package org.defendev.common.spring6.security;

import org.springframework.security.core.AuthenticationException;



public class QuasiLoginAuthenticationException extends AuthenticationException {

    public QuasiLoginAuthenticationException(String msg) {
        super(msg);
    }

}

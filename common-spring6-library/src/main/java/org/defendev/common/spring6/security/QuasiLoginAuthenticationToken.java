package org.defendev.common.spring6.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;



public class QuasiLoginAuthenticationToken implements Authentication {

    private final UserDetails principal;

    private final List<SimpleGrantedAuthority> authorities;

    private final boolean authenticated;

    public QuasiLoginAuthenticationToken(UserDetails principal, List<SimpleGrantedAuthority> authorities,
                                         boolean authenticated) {
        this.principal = principal;
        this.authorities = authorities;
        this.authenticated = authenticated;
    }

    public QuasiLoginAuthenticationToken authenticated() {
        return new QuasiLoginAuthenticationToken(principal, authorities, true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Designed to be immutable. Copy this object rather.");
    }

    @Override
    public String getName() {
        return principal.getUsername();
    }

}

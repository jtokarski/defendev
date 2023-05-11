package org.defendev.common.domain.useridentity.service.dto;

import java.util.Set;


public class AuthenticationDto implements IAuthenticationDto {

    private final Set<IGrantedAuthorityDto> authorities;

    private final IUserDetailsDto principal;

    private final boolean authenticated;

    public AuthenticationDto(Set<IGrantedAuthorityDto> authorities, IUserDetailsDto principal, boolean authenticated) {
        this.authorities = authorities;
        this.principal = principal;
        this.authenticated = authenticated;
    }

    @Override
    public Set<IGrantedAuthorityDto> getAuthorities() {
        return authorities;
    }

    @Override
    public IUserDetailsDto getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
}

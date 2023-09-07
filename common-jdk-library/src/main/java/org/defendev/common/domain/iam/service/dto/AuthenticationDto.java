package org.defendev.common.domain.iam.service.dto;

import java.util.Set;


/**
 * DTO containing authenticated (logged-id) user information that is safe to disclose to
 * web front-end single-page application. Loosely based on org.springframework.security.core.Authentication
 * from Spring Security.
 */
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

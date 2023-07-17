package org.defendev.common.domain.iam.service.dto;


import java.util.Collection;

/*
 * By design, a subset of org.springframework.security.core.Authentication interface.
 * Dedicated for safe, external use (disclosure to web client).
 *
 */
public interface IAuthenticationDto {

    Collection<IGrantedAuthorityDto> getAuthorities();

    IUserDetailsDto getPrincipal();

    boolean isAuthenticated();

}

package org.defendev.common.domain.useridentity.service.dto;

/*
 * By design, a subset of org.springframework.security.core.context.SecurityContext interface.
 * Dedicated for safe, external use (disclosure to web client).
 *
 */
public interface ISecurityContextDto {

    IAuthenticationDto getAuthentication();

}

package org.defendev.common.domain.iam.service.dto;

/*
 * By design, a subset of org.springframework.security.core.GrantedAuthority interface.
 * Dedicated for safe, external use (disclosure to web client).
 *
 */
public interface IGrantedAuthorityDto {

    String getAuthority();

}

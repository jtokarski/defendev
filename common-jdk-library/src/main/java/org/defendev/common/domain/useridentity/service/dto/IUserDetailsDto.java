package org.defendev.common.domain.useridentity.service.dto;

/*
 * By design, a subset of org.springframework.security.core.userdetails.UserDetails interface.
 * Dedicated for safe, external use (disclosure to web client).
 *
 */
public interface IUserDetailsDto {

    String getUsername();

}

package org.defendev.common.domain.iam;

import java.util.Map;
import java.util.Set;


/**
 * <p>Intended to be implemented by classes used in Spring Security extension points:</p>
 * <ul>
 *   <li>returned by org.springframework.security.core.userdetails.UserDetailsService.loadUserByUsername()</li>
 *   <li>returned by org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService.loadUser()</li>
 *   <li>stored in security context - accessible by SecurityContextHolder.getContext().getAuthentication().getPrincipal()</li>
 * </ul>
 *
 * In contrast to {@link org.defendev.common.domain.iam.service.dto.IUserDetailsDto}, objects implementing this
 * interface are intended for internal use and <strong>not</strong> for disclosure to web clients.
 *
 */
public interface IDefendevUserDetails {

    String getUsername();

    Set<String> getRoles();

    Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit();

}

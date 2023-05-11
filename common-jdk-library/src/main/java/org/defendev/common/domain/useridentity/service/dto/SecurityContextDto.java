package org.defendev.common.domain.useridentity.service.dto;



public class SecurityContextDto implements ISecurityContextDto {

    private final IAuthenticationDto authentication;

    public SecurityContextDto(IAuthenticationDto authentication) {
        this.authentication = authentication;
    }

    @Override
    public IAuthenticationDto getAuthentication() {
        return authentication;
    }

}

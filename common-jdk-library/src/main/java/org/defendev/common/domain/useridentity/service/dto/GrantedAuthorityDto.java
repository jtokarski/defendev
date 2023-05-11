package org.defendev.common.domain.useridentity.service.dto;



public class GrantedAuthorityDto implements IGrantedAuthorityDto {

    private final String authority;

    public GrantedAuthorityDto(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

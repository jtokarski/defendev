package org.defendev.common.domain.iam.service.dto;


public class UserDetailsDto implements IUserDetailsDto {

    private final String username;

    public UserDetailsDto(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

}

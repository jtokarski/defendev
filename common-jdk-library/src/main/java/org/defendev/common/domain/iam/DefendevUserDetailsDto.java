package org.defendev.common.domain.iam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;



public class DefendevUserDetailsDto implements IDefendevUserDetails {

    private String username;

    private Set<String> roles;

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DefendevUserDetailsDto(
        @JsonProperty("username") String username,
        @JsonProperty("roles") Set<String> roles,
        @JsonProperty("privilegeToOwnershipUnit") Map<Privilege, Set<Long>> privilegeToOwnershipUnit
    ) {
        this.username = username;
        this.roles = roles;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }

}

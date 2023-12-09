package org.defendev.common.domain.iam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;



public class DefendevUserDetailsDto implements IDefendevUserDetails {

    private String username;

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DefendevUserDetailsDto(
        @JsonProperty("username") String username,
        @JsonProperty("privilegeToOwnershipUnit") Map<Privilege, Set<Long>> privilegeToOwnershipUnit
    ) {
        this.username = username;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }

}

package org.defendev.common.spring6.security;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.springframework.security.core.userdetails.User;

import java.util.Map;
import java.util.Set;



public class QuasiUserDetails extends User implements IDefendevUserDetails {

    public QuasiUserDetails(String username) {
        super(username, "", true, true, true, true, Set.of());
    }

    @Override
    public Set<String> getRoles() {
        return Set.of();
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return Map.of();
    }

}

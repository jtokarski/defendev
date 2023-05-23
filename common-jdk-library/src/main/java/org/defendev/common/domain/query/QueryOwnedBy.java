package org.defendev.common.domain.query;

import java.util.Set;


public interface QueryOwnedBy {

    /**
     * Specifies, if the service handling the query should search for all items visible to the requesting user.
     *
     * @return
     *   {@code true} - if the service handling the query should resolve ownershipUnitExternalIds for the requesting user.
     *      In this case {@link #getOwnershipUnitExternalIds()} is ignored.<br />
     *   {@code false} - if the service handling the query should honor {@link #getOwnershipUnitExternalIds()}
     */
    boolean getResolveOwnershipUnitsForRequestingUser();

    Set<String> getOwnershipUnitExternalIds();

}

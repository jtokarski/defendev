package org.defendev.common.domain.iam;

import java.io.Serializable;

/**
 * Intended to be implemented by JPA @Entity classess.
 *
 */
public interface IOwnedBy<ID extends Serializable> {

    ID getOwnershipUnitId();

    void setOwnershipUnitId(ID ownershipUnitId);

}

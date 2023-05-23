package org.defendev.common.domain.useridentity;

import java.io.Serializable;


public interface IOwnedBy<ID extends Serializable> {

    ID getOwnershipUnitId();

    void setOwnershipUnitId(ID ownershipUnitId);

}

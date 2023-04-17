package org.defendev.common.infra;


public interface ExternalIdConverter<T_INTERNAL_ID> {

    String toExternalId(T_INTERNAL_ID internalId);

    T_INTERNAL_ID toInternalId(String externalId);

}

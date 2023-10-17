package org.defendev.common.infra;



public interface IExternalIdConverter<T_INTERNAL_ID> {

    String toExternalId(T_INTERNAL_ID internalId);

    T_INTERNAL_ID toInternalId(String externalId);

}

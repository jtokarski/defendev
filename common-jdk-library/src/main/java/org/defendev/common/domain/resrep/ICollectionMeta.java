package org.defendev.common.domain.resrep;

public interface ICollectionMeta {

    int NO_SUCH_PAGE = -1;

    Integer getPageNumber();

    Integer getPageSize();

    Integer getTotalElements();

}

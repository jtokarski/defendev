package org.defendev.common.domain.query;


public interface QueryPageable {

    Integer getPageNumber();

    Integer getPageSize();

    boolean getUnpaged();

    default long getStartElementIndex() {
        return getPageNumber() * getPageSize();
    }

}

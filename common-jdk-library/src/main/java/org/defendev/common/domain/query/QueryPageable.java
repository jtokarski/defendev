package org.defendev.common.domain.query;


public interface QueryPageable {

    int getPageNumber();

    int getPageSize();

    default long getStartElementIndex() {
        return getPageNumber() * getPageSize();
    }

}

package org.defendev.common.domain.resrep;



public class CollectionMeta implements ICollectionMeta {

    private final int pageNumber;

    private final int pageSize;

    private final int totalElements;

    public CollectionMeta(int pageNumber, int pageSize, int totalElements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    @Override
    public Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public Integer getTotalElements() {
        return totalElements;
    }

}

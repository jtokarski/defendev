package org.defendev.common.domain.resrep;



public class CollectionMeta implements ICollectionMeta {

    private final Integer pageNumber;

    private final Integer pageSize;

    private final boolean unpaged;

    private final Integer totalElements;

    private CollectionMeta(Integer pageNumber, Integer pageSize, boolean unpaged, Integer totalElements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.unpaged = unpaged;
        this.totalElements = totalElements;
    }

    public static CollectionMeta paged(int pageNumber, int pageSize, int totalElements) {
        return new CollectionMeta(pageNumber, pageSize, false, totalElements);
    }

    public static CollectionMeta unpaged(int totalElements) {
        return new CollectionMeta(null, null, true, totalElements);
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
    public boolean getUnpaged() {
        return unpaged;
    }

    @Override
    public Integer getTotalElements() {
        return totalElements;
    }

}

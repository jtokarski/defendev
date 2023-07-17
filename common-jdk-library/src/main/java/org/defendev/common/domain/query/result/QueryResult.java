package org.defendev.common.domain.query.result;



public class QueryResult<T> {

    public enum Status {
        REQUEST_INVALID,
        NOT_FOUND,
        EMPTY,
        SUCCESS;

        // Intentionally removing (comment-out) the INSUFFICIENT_PERMISSIONS value as I favour immediate
        // throwing of org.springframework.security.access.AccessDeniedException.
        // INSUFFICIENT_PERMISSIONS,
    }

    public static <T> QueryResult<T> notFound() {
        return new QueryResult<>(Status.NOT_FOUND, null, null);
    }

    public static <T> QueryResult<T> success(T data) {
        return new QueryResult<>(Status.SUCCESS, null, data);
    }

    private final Status status;

    /*
     * Meant to be an additional clarification to the 'status' field. Also, with growing number of various
     * use cases, expected to evolve into new standardized values in the 'Status' enum.
     */
    private final String statusDetail;

    /*
     * The 'data' is usually (but not always) some implementation of IBaseDto or ICollectionResRep
     */
    private final T data;

    public QueryResult(Status status, String statusDetail, T data) {
        this.status = status;
        this.statusDetail = statusDetail;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return Status.SUCCESS == status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public T getData() {
        return data;
    }

}

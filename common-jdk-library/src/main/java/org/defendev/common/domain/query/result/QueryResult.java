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
        return new QueryResult<>(Status.NOT_FOUND, null, null, null);
    }

    public static <T> QueryResult<T> success(T data) {
        return new QueryResult<>(Status.SUCCESS, null, null, data);
    }

    private final Status status;

    /*
     * Meant to be an additional clarification to the 'status' field. Also, with growing number of various
     * use cases, expected to evolve into new standardized values in the 'Status' enum.
     */
    private final String statusDetail;

    /*
     * Message related with (supplementing) the 'status' in a form that can be displayed to the end user.
     * Usually will be present for 'status' different than SUCCESS
     */
    private final String statusUserMessage;

    /*
     * The 'data' is usually (but not always) some implementation of IBaseDto or ICollectionResRep
     */
    private final T data;

    public QueryResult(Status status, String statusDetail, String statusUserMessage, T data) {
        this.status = status;
        this.statusDetail = statusDetail;
        this.statusUserMessage = statusUserMessage;
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

    public String getStatusUserMessage() {
        return statusUserMessage;
    }

    public T getData() {
        return data;
    }

}

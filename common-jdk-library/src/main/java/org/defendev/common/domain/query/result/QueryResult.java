package org.defendev.common.domain.query.result;


public class QueryResult<T> {

    public enum Status {
        REQUEST_INVALID,
        INSUFFICIENT_PERMISSIONS,
        NOT_FOUND,
        EMPTY,
        SUCCESS;
    }

    public static <T> QueryResult<T> notFound() {
        return new QueryResult<>(Status.NOT_FOUND, null, null);
    }

    public static <T> QueryResult<T> success(T data) {
        return new QueryResult<>(Status.SUCCESS, null, data);
    }

    private final Status status;

    private final String statusDetail;

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

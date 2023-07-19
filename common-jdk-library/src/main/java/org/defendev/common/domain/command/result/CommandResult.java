package org.defendev.common.domain.command.result;



public class CommandResult<T> {

    public enum Status {
        REQUEST_INVALID,
        AGGREGATE_NOT_FOUND,

        /*
         * Some time in the future, I would like to have implementation of this mechanism of letting know that
         * a service have accepted the Command, but it's still being processed. And let the client know that they
         * can subscribe to a queue/topic/Websocket to obtain result when it's available.
         *
         */
        ACCEPTED_FOR_PROCESSING,     // to be processed by "this" service
        DISPATCHED_FOR_PROCESSING,   // to be processed by "some other" service

        SUCCESS_NO_MODIFICATION,
        SUCCESS;

        // Intentionally removing (comment-out) the INSUFFICIENT_PERMISSIONS value as I favour immediate
        // throwing of org.springframework.security.access.AccessDeniedException.
        // INSUFFICIENT_PERMISSIONS,
    }

    public static <T> CommandResult<T> requestInvalid(String statusDetail, String statusUserMessage) {
        return new CommandResult<>(Status.REQUEST_INVALID, statusDetail, statusUserMessage, null);
    }

    public static <T> CommandResult<T> aggregateNotFound() {
        return new CommandResult<>(Status.AGGREGATE_NOT_FOUND, null, null, null);
    }

    public static <T> CommandResult<T> success(T data) {
        return new CommandResult<>(Status.SUCCESS, null, null, data);
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

    private final T data;

    public CommandResult(Status status, String statusDetail, String statusUserMessage, T data) {
        this.status = status;
        this.statusDetail = statusDetail;
        this.statusUserMessage = statusUserMessage;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return Status.SUCCESS == status || Status.SUCCESS_NO_MODIFICATION == status;
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

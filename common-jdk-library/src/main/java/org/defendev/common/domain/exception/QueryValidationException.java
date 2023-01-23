package org.defendev.common.domain.exception;



public class QueryValidationException extends RuntimeException {

    private Status status;

    /*
     * Consider going back to the idea of using Jakarta Bean Validation:
     *
     * import jakarta.validation.ConstraintViolation;
     *
     * private Set<ConstraintViolation<? extends Query>> queryViolations;
     *
     */

    public static enum Status {
        REQUEST_INVALID,
        PAGINATION_PARAMS_INVALID;
    }

    public QueryValidationException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public QueryValidationException(
        String message,
        Throwable cause,
        Status status
    ) {
        super(message, cause);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

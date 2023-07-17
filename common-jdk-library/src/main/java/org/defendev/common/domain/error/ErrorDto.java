package org.defendev.common.domain.error;



public final class ErrorDto {

    private final String code;

    private final String message;

    private final String detail;

    /*
     * The exceptionId is intended to be visible for the user, and the goal is to improve the quality of
     * bug reporting by the user.
     */
    private final String exceptionId;

    public ErrorDto(String code, String message, String detail, String exceptionId) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.exceptionId = exceptionId;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}


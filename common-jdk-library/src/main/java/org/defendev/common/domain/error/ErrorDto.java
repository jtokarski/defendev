package org.defendev.common.domain.error;



public final class ErrorDto {

    private final String code;

    private final String message;

    private final String detail;

    /*
     * End-user-friendly message.
     */
    private final String userMessage;

    /*
     * The exceptionId is intended to be visible for the user, and the goal is to improve the quality of
     * bug reporting by the user.
     */
    private final String exceptionId;

    public ErrorDto(String code, String message, String detail, String userMessage, String exceptionId) {
        this.code = "" + code;
        this.message = "" + message;
        this.detail = "" + detail;
        this.userMessage = userMessage;
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

    public String getUserMessage() {
        return userMessage;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}


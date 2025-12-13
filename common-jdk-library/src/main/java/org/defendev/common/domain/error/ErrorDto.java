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
     * The ONLY purpose of this exceptionId is to quickly match user's screenshot in bug report with log line.
     * No deeper, broader other concept or strategy behind this.
     * So, whenever used make sure that:
     *   - it is logged (for example inside @ControllerAdvice class)
     *   - it is made visible to the user.=
     *
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


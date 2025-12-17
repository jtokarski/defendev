package org.defendev.common.domain.exception;

import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.error.ErrorWrapperDto;

import java.util.List;

import static java.util.Objects.nonNull;



/**
 * Generic application exception, caused by bad user request, which doesn't fall into any other
 * more specific category.
 *
 */
public class DefendevIllegalArgumentException extends RuntimeException implements IdentifiableException {

    private ErrorWrapperDto errorWrapperDto;

    private final String exceptionId;

    public DefendevIllegalArgumentException(ErrorDto errorDto) {
        this(errorDto, false);
    }

    public DefendevIllegalArgumentException(ErrorDto errorDto, boolean doGenerateExceptionId) {
        this.errorWrapperDto = new ErrorWrapperDto(errorDto);
        if (doGenerateExceptionId) {
            exceptionId = IdentifiableException.generateExceptionId();
        } else {
            exceptionId = "";
        }
    }

    public DefendevIllegalArgumentException(ErrorWrapperDto errorWrapperDto) {
        this(errorWrapperDto, false);
    }

    public DefendevIllegalArgumentException(ErrorWrapperDto errorWrapperDto, boolean doGenerateExceptionId) {
        if (nonNull(errorWrapperDto)) {
            this.errorWrapperDto = errorWrapperDto;
        } else {
            this.errorWrapperDto = new ErrorWrapperDto(List.of());
        }
        if (doGenerateExceptionId) {
            exceptionId = IdentifiableException.generateExceptionId();
        } else {
            exceptionId = "";
        }
    }

    @Override
    public String getExceptionId() {
        return exceptionId;
    }

    public ErrorWrapperDto getErrorWrapperDto() {
        return errorWrapperDto;
    }

}

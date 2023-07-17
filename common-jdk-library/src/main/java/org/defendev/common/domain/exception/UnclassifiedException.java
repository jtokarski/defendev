package org.defendev.common.domain.exception;

import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.error.ErrorWrapperDto;



public class UnclassifiedException extends RuntimeException {

    private ErrorWrapperDto errorWrapperDto;

    public UnclassifiedException(ErrorDto errorDto) {
        this.errorWrapperDto = new ErrorWrapperDto(errorDto);
    }

    public UnclassifiedException(ErrorWrapperDto errorWrapperDto) {
        this.errorWrapperDto = errorWrapperDto;
    }

    public ErrorWrapperDto getErrorWrapperDto() {
        return errorWrapperDto;
    }

}

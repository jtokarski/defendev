package org.defendev.common.domain.exception;

import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.error.ErrorWrapperDto;


public class ResourceIdMismatchException extends RuntimeException {

    private ErrorWrapperDto errorWrapperDto;

    public ResourceIdMismatchException() {
        this.errorWrapperDto = new ErrorWrapperDto(new ErrorDto("RESOURCE_ID_MISMATCH", "Resource Id Mismatch", "",
            "Resource Id Mismatch", null));
    }

    public ErrorWrapperDto getErrorWrapperDto() {
        return errorWrapperDto;
    }

}

package org.defendev.common.domain.error;

import java.util.List;
import java.util.ArrayList;



public class ErrorWrapperDto {

    private final List<ErrorDto> errors;

    public ErrorWrapperDto(ErrorDto primaryError) {
        errors = new ArrayList<ErrorDto>();
        errors.add(primaryError);
    }

    public ErrorWrapperDto(List<ErrorDto> errors) {
        this.errors = errors;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }
}

package org.defendev.common.domain.error;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class ErrorWrapperDto {

    private final List<ErrorDto> errors;

    public ErrorWrapperDto(ErrorDto primaryError) {
        errors = List.of(primaryError);
    }

    public ErrorWrapperDto(List<ErrorDto> errors) {
        this.errors = errors;
    }

    public ErrorWrapperDto append(ErrorWrapperDto wrapperDto) {
        final List<ErrorDto> allErrors = Stream.of(errors, wrapperDto.getErrors())
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return new ErrorWrapperDto(allErrors);
    }

    public ErrorWrapperDto append(ErrorDto errorDto) {
        final List<ErrorDto> allErrors = Stream.concat(errors.stream(), Stream.of(errorDto))
            .collect(Collectors.toList());
        return new ErrorWrapperDto(allErrors);
    }

    public ErrorWrapperDto prepend(ErrorDto errorDto) {
        final List<ErrorDto> allErrors = Stream.concat(Stream.of(errorDto), errors.stream())
            .collect(Collectors.toList());
        return new ErrorWrapperDto(allErrors);
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }
}

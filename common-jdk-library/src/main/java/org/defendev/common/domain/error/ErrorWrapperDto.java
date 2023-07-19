package org.defendev.common.domain.error;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;



public class ErrorWrapperDto {

    private final List<ErrorDto> errors;

    public ErrorWrapperDto(ErrorDto primaryError) {
        if (nonNull(primaryError)) {
            errors = List.of(primaryError);
        } else {
            this.errors = List.of();
        }
    }

    public ErrorWrapperDto(List<ErrorDto> errors) {
        if (nonNull(errors)) {
            this.errors = errors;
        } else {
            this.errors = List.of();
        }
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

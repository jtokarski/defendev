package org.defendev.common.spring6.web.controlleradvice;

import org.defendev.common.domain.command.result.CommandResult;
import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.error.ErrorWrapperDto;
import org.defendev.common.domain.exception.CommandFailedException;
import org.defendev.common.domain.exception.DefendevIllegalArgumentException;
import org.defendev.common.domain.exception.DefendevIllegalStateException;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;



public class ExceptionHandlers {

    public enum DetailProfile {
        non_prod,
        prod
    }

    private enum ErrorCode {
        query_failed_result_unknown,
        query_failed_status_unknown,
        query_failed_request_invalid,
        query_failed_not_found,
        command_failed_result_unknown,
        command_failed_status_unknown,
        command_failed_request_invalid,
        command_failed_aggregate_not_found,
        exception_id
    }

    /*
     * One static handler method per exception class. Each with possibility to specify:
     *   - detail profile - how verbose the error dto should be (not yet implemented, ignored for now)
     *   - contentType - the response content type
     *
     *
     */
    public static ResponseEntity<ErrorWrapperDto> toResponseEntity(QueryFailedException exception,
                                                                   DetailProfile profile, MediaType contentType) {
        final QueryResult<?> queryResult = exception.getQueryResult();
        
        if (isNull(queryResult)) {
            final ErrorDto errorDto = new ErrorDto(
                ErrorCode.query_failed_result_unknown.name(),
                "Query failed with unknown result",
                "queryResult is null",
                "Unexpected application error occurred",
                exception.getExceptionId()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(contentType)
                .body(new ErrorWrapperDto(errorDto));
        }

        final QueryResult.Status status = queryResult.getStatus();

        return switch (status) {
            case NOT_FOUND -> {
                HttpStatus httpStatus = HttpStatus.NOT_FOUND;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.query_failed_not_found.name(),
                    httpStatus.getReasonPhrase(),
                    queryResult.getStatusDetail(),
                    queryResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
            case REQUEST_INVALID -> {
                final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.query_failed_request_invalid.name(),
                    httpStatus.getReasonPhrase(),
                    queryResult.getStatusDetail(),
                    queryResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
            case null, default -> {
                final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.query_failed_status_unknown.name(),
                    httpStatus.getReasonPhrase(),
                    queryResult.getStatusDetail(),
                    queryResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
        };
    }

    public static ResponseEntity<ErrorWrapperDto> toResponseEntity(CommandFailedException exception,
                                                                   DetailProfile profile, MediaType contentType) {
        final CommandResult<?> commandResult = exception.getCommandResult();

        if (isNull(commandResult)) {
            final ErrorDto errorDto = new ErrorDto(
                ErrorCode.command_failed_result_unknown.name(),
                "Command failed with unknown result",
                "commandResult is null",
                "Unexpected application error occurred",
                exception.getExceptionId()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(contentType)
                .body(new ErrorWrapperDto(errorDto));
        }

        final CommandResult.Status status = commandResult.getStatus();

        return switch(status) {
            case REQUEST_INVALID -> {
                final HttpStatus httpStatus = HttpStatus.CONFLICT;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.command_failed_request_invalid.name(),
                    httpStatus.getReasonPhrase(),
                    commandResult.getStatusDetail(),
                    commandResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
            case AGGREGATE_NOT_FOUND -> {
                final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.command_failed_aggregate_not_found.name(),
                    "Aggregate not found",
                    commandResult.getStatusDetail(),
                    commandResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
            case null, default -> {
                final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                final ErrorDto errorDto = new ErrorDto(
                    ErrorCode.command_failed_status_unknown.name(),
                    httpStatus.getReasonPhrase(),
                    commandResult.getStatusDetail(),
                    commandResult.getStatusUserMessage(),
                    exception.getExceptionId()
                );
                yield ResponseEntity.status(httpStatus)
                    .contentType(contentType)
                    .body(new ErrorWrapperDto(errorDto));
            }
        };
    }

    public static ResponseEntity<ErrorWrapperDto> toResponseEntity(DefendevIllegalStateException exception,
                                                                   DetailProfile profile, MediaType contentType) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorWrapperDto errorWrapperDto;
        if (nonNull(exception.getExceptionId())) {
            final ErrorDto exceptionIdDto = new ErrorDto(
                ErrorCode.exception_id.name(),
                httpStatus.getReasonPhrase(),
                "",
                "",
                exception.getExceptionId()
            );
            errorWrapperDto = exception.getErrorWrapperDto().prepend(exceptionIdDto);
        } else {
            errorWrapperDto = exception.getErrorWrapperDto();
        }
        return ResponseEntity.status(httpStatus)
            .contentType(contentType)
            .body(errorWrapperDto);
    }

    public static ResponseEntity<ErrorWrapperDto> toResponseEntity(DefendevIllegalArgumentException exception,
                                                                   DetailProfile profile, MediaType contentType) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorWrapperDto errorWrapperDto;
        if (nonNull(exception.getExceptionId())) {
            final ErrorDto exceptionIdDto = new ErrorDto(
                ErrorCode.exception_id.name(),
                httpStatus.getReasonPhrase(),
                "",
                "",
                exception.getExceptionId()
            );
            errorWrapperDto = exception.getErrorWrapperDto().prepend(exceptionIdDto);
        } else {
            errorWrapperDto = exception.getErrorWrapperDto();
        }
        return ResponseEntity.status(httpStatus)
            .contentType(contentType)
            .body(errorWrapperDto);
    }

}

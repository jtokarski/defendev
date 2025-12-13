package org.defendev.common.domain.exception;

import org.defendev.common.domain.command.result.CommandResult;



public class CommandFailedException extends RuntimeException implements IdentifiableException {

    private final CommandResult<?> commandResult;

    private final String exceptionId;

    public CommandFailedException(CommandResult<?> commandResult) {
        this(commandResult, false);
    }

    public CommandFailedException(CommandResult<?> commandResult, boolean doGenerateExceptionId) {
        this.commandResult = commandResult;
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

    public CommandResult<?> getCommandResult() {
        return commandResult;
    }

}

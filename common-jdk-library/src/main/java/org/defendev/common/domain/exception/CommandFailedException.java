package org.defendev.common.domain.exception;

import org.defendev.common.domain.command.result.CommandResult;



public class CommandFailedException extends RuntimeException {

    private CommandResult<?> commandResult;

    public CommandFailedException(CommandResult<?> commandResult) {
        this.commandResult = commandResult;
    }

    public CommandResult<?> getCommandResult() {
        return commandResult;
    }

}

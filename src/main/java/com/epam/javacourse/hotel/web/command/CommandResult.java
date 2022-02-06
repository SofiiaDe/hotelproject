package com.epam.javacourse.hotel.web.command;

public class CommandResult implements ICommandResult {
    private final String address;
    private final CommandResultType commandResultType;

    public CommandResult(CommandResultType commandResultType, String address) {
        this.commandResultType = commandResultType;
        this.address = address;
    }

    public CommandResultType getType() {
        return commandResultType;
    }

    public String getAddress() {
        return address;
    }
}


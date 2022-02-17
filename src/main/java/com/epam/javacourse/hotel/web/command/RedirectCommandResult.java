package com.epam.javacourse.hotel.web.command;

public class RedirectCommandResult extends CommandResult {
    public RedirectCommandResult(String address) {
        super(CommandResultType.REDIRECT, address);
    }
}

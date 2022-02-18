package com.epam.javacourse.hotel.web.command;

/**
 * Provides result of command's execution as a redirect type
 */
public class RedirectCommandResult extends CommandResult {
    public RedirectCommandResult(String address) {
        super(CommandResultType.REDIRECT, address);
    }
}

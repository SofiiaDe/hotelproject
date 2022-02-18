package com.epam.javacourse.hotel.web.command;

/**
 * Provides result of command's execution as address type
 */
public class AddressCommandResult extends CommandResult {
    public AddressCommandResult(String address) {
        super(CommandResultType.ADDRESS, address);
    }
}

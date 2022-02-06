package com.epam.javacourse.hotel.web.command;

public class AddressCommandResult extends CommandResult {
    public AddressCommandResult(String address) {
        super(CommandResultType.Address, address);
    }
}

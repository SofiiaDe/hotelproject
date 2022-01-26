package com.epam.javacourse.hotel.web.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static CommandFactory commandFactory = new CommandFactory();
    private static Map<String, ICommand> commands = new HashMap<>();

    private CommandFactory() {

    }

    /**
     * singleton pattern
     * @return
     */
    public static CommandFactory commandFactory() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory();
        }
        return commandFactory;
    }

    static {

        //client commands
        commands.put("user_profile", new UserProfileCommand());

        //manager commands
        commands.put("allUsersList", new GetAllUsersCommand());

        //common commands
        commands.put("registration", new RegistrationCommand());
    }

    public static ICommand getCommand(String commandName) {
        return commands.get(commandName);
    }
}
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
        commands.put("clientAccount", new ClientAccountPageCommand());

        //manager commands
        commands.put("allUsersList", new GetAllUsersCommand());

        //common commands
        commands.put("registerPage", new RegisterPageCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("login", new LoginCommand());
        commands.put("loginPage", new LoginPageCommand());

    }

    public static ICommand getCommand(String commandName) {
        return commands.get(commandName);
    }
}
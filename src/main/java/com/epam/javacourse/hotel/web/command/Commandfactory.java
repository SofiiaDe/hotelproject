package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.web.command.client.*;
import com.epam.javacourse.hotel.web.command.common.*;
import com.epam.javacourse.hotel.web.command.manager.EditRoomsCommand;
import com.epam.javacourse.hotel.web.command.manager.GetAllUsersCommand;

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
        commands.put("applicationPage", new ApplicationPageCommand());
        commands.put("submitApplication", new SubmitApplicationCommand());
        commands.put("freeRoomsPage", new FreeRoomsPageCommand());
        commands.put("bookRoom", new BookRoomCommand());
        commands.put("editApplication", new EditApplicationCommand());

        //manager commands
        commands.put("allUsersList", new GetAllUsersCommand());
        commands.put("editRooms", new EditRoomsCommand());

        //common commands
        commands.put("registerPage", new RegisterPageCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("login", new LoginCommand());
        commands.put("loginPage", new LoginPageCommand());
        commands.put("logout", new LogoutCommand());

    }

    public static ICommand getCommand(String commandName) {
        return commands.get(commandName);
    }
}
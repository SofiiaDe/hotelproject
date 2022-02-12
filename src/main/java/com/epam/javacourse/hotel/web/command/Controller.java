package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        process(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);

    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String address;

        String commandName = request.getParameter("command");

        ICommand command = CommandFactory.getCommand(commandName);
        ICommandResult commandResult;

        try {
            commandResult = command.execute(request, response);
        } catch (AppException e) {
            logger.error("AppException occurred", e);
            request.setAttribute("errorMessage", e.getMessage());
            commandResult = new AddressCommandResult(Path.PAGE_ERROR);

        } catch (Exception e) {
            logger.error(e);
            request.setAttribute("errorMessage", "Smth went wrong");
            commandResult = new AddressCommandResult(Path.PAGE_ERROR);
        }

        if (commandResult == null){
            address = Path.PAGE_ERROR;
            request.setAttribute("errorMessage", "No result available.");
        }else{
            switch (commandResult.getType()){
                case Address:
                    address = ((AddressCommandResult)commandResult).getAddress();
                    break;
                case Redirect:
                    response.sendRedirect(((RedirectCommandResult)commandResult).getAddress());
                    return;
                default:
                    address = Path.PAGE_ERROR;
                    request.setAttribute("errorMessage", "Unknown command result type.");
            }
        }

        request.getRequestDispatcher(address).forward(request, response);
    }

}

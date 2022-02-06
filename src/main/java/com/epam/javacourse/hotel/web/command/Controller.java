package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Controller extends HttpServlet {

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
        ICommandResult commandResult = null;

        try {
            commandResult = command.execute(request, response);
        } catch (AppException e) {
            if (e.getCause() != null) {
                request.setAttribute("errorMessage", e.getMessage() + ": "
                        + e.getCause().getMessage());
            } else {
                request.setAttribute("errorMessage", e.getMessage());
            }
        }

        if (commandResult == null){
            address = Path.PAGE_ERROR;
            request.setAttribute("errorMessage", "Null command result.");
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
                    request.setAttribute("errorMessage", "Unknown command result type");
            }
        }

        request.getRequestDispatcher(address).forward(request, response);
    }

}

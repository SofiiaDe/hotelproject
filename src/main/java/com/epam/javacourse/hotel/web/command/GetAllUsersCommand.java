package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllUsersCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


        UserDAO db = UserDAO.getInstance();
        List<User> list = db.findAllUsers();
        session.setAttribute("usersList", list); //http://localhost:8080/Hotel/controller?command=test
        return Path.PAGE_GET_USERS;
    }
}

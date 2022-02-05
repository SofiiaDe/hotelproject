package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService {

    List<User> findAllUsers() throws DBException;

    void create(User user) throws DBException;

    User findUserByEmail(String email) throws DBException;

    User findUserById(int id) throws DBException;
//
//
//    void update(User user) throws DBException;
//
//    void remove(User user) throws DBException;
//
//
//    List<Application> findUserApplications(User user) throws DBException;


//    void saveLinksUserHasRooms(User user, String[] tariffsId);
//
//    void removeLinksUsersHasRooms(User user);

//    void updateAuthorisedUserToSession(HttpServletRequest request, HttpSession session, User user) throws DBException;
}

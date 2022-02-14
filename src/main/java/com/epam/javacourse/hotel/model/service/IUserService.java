package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.User;


import java.util.List;

public interface IUserService {

    List<User> getAllUsers() throws AppException;

    void create(User user) throws AppException;

    User getUserByEmail(String email) throws AppException;

    User getUserById(int id) throws AppException;

}

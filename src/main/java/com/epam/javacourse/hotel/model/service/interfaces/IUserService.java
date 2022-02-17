package com.epam.javacourse.hotel.model.service.interfaces;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers() throws AppException;

    void create(User user) throws AppException;

    User getUserByEmail(String email) throws AppException;

    User getUserById(int id) throws AppException;

}

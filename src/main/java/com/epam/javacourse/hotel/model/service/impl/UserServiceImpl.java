package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws AppException {
        try {
            return this.userDao.findAllUsers();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all users", exception);
        }
    }

    @Override
    public void createUser(User user) throws AppException {
        try {
            this.userDao.createUser(user);
        } catch (DBException exception) {
            throw new AppException("Can't create a user", exception);
        }
    }

    @Override
    public User getUserByEmail(String email) throws AppException {
        try {
            return this.userDao.findUserByEmail(email);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve user by email", exception);
        }
    }

    @Override
    public User getUserById(int id) throws AppException {
        try {
            return this.userDao.findUserById(id);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve user by id", exception);
        }

    }


}

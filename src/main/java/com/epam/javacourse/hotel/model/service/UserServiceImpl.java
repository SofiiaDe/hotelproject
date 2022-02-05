package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllUsers() throws DBException {
        return this.userDao.findAllUsers();
    }

    @Override
    public void create(User user) throws DBException {
        this.userDao.createUser(user);
    }

    @Override
    public User findUserByEmail(String email) throws DBException {
        return this.userDao.getUserByEmail(email);
    }

    @Override
    public User findUserById(int id) throws DBException {
        return this.userDao.getUserById(id);

    }


//
//    @Override
//    public void update(User user) throws DBException {
//        this.userDao.updateUser(user);
//    }
//
//    @Override
//    public void remove(User user) throws DBException {
//        this.userDao.deleteUsers(user);
//    }
//

//
//    @Override
//    public List<Application> findUserApplications(User user) throws DBException {
//        return this.userDao.getApplications(user);
//    }



}

package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final UserDAO userDao;
//    private final IAccountService accountService;

    //    public UserServiceImpl(UserDAO userDao, IAccountService accountService) {
    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
//        this.accountService = accountService;
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
    public User findByEmail(String email) throws DBException {
        return this.userDao.getUserByEmail(email);
    }

//    findAllRoomsByUserID room!/user
//    + AccountPageCommand (get)

//    @Override
//    public List<User> findAllAuthorisedInfo() throws DBException {
//        List<User> users = findAllUsers();
//        List<User> authorisedUsers = new ArrayList<>();
//        for (User user : users) {
//            authorisedUsers.add(userToAuthorisedUser(user));
//        }
//        return authorisedUsers;
//    }
//
//    @Override
//    public User findByLoginAuthorisedInfo(String login) throws DBException {
//        return userToAuthorisedUser(findByLogin(login));
//    }
//
//    @Override
//    public User find(int id) throws DBException {
//        return this.userDao.getUserById(id);
//    }
//

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
//
//    @Override
//    public void updateAuthorisedUserToSession(HttpServletRequest request, HttpSession session, User user) throws DBException {
//        User authorisedUser = userToAuthorisedUser(user);
//        session.setAttribute("authorisedUser", authorisedUser);
//        request.setAttribute("authorisedUser", authorisedUser);
//    }
//
//    private User userToAuthorisedUser(User user) throws DBException {
//        user.setAccount(accountService.find(user.getAccount().getId()));
//        user.setApplications(new HashSet<>(findUserApplications(user)));
//        return user;
//    }
}

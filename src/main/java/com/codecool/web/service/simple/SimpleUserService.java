package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;

import java.security.Provider;
import java.sql.SQLException;

public final class SimpleUserService implements UserService {

    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                throw new ServiceException("Bad login");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public User findUserById(int id) throws SQLException, ServiceException {
        try {
            return userDao.findUserById(id);
        }
        catch (IllegalArgumentException e) {
            throw new ServiceException("Not Found!");
        }
    }

    @Override
    public void registerUser(User user) throws SQLException {
        userDao.registerUser(user);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userDao.updateUser(user);
    }
}

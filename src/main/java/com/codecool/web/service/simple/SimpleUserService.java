package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;

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
    public User findByEmail(String email) throws SQLException, ServiceException {
        if(email.isEmpty()) {
            throw new ServiceException("No mail address provided!");
        }
        return userDao.findByEmail(email);
    }

    @Override
    public User registerUser(String email, String password, String name, String country, String city, String street, String zip, int money, boolean status) throws SQLException, ServiceException {
        try{
            userDao.isRegistered(email);
            return userDao.registerUser(email, password, name, country, city, street, zip, money, status);
        } catch (NullPointerException e) {
            throw new ServiceException(e.getMessage() + "Please Use All Fields!");
        }

    }

    @Override
    public User updateUser(int id, String email, String name, String country, String city, String street, String zip, String money) throws SQLException, ServiceException {
        try{
            userDao.updateUser(id, email, name, country, city, street, zip, Integer.valueOf(money));
            return findByEmail(email);
        } catch (NumberFormatException e) {
            throw new ServiceException("Money: Should Be A Number!");
        }

    }
}

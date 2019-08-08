package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService {

    User loginUser(String email, String password) throws SQLException, ServiceException;

    User findUserById(int id) throws SQLException, ServiceException;

    User registerUser(String email, String password, String name, String country, String city, String street, String zip, int money, boolean status) throws SQLException;

    void updateUser(User user) throws SQLException;
}

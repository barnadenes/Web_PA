package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService {

    User loginUser(String email, String password) throws SQLException, ServiceException;

    User findUserById(int id) throws SQLException, ServiceException;

    void registerUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;
}

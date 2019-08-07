package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;

public interface UserDao {

    User findByEmail(String email) throws SQLException;

    User findUserById(int id) throws SQLException;

    void registerUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    void isRegistered(String email) throws SQLException;
}

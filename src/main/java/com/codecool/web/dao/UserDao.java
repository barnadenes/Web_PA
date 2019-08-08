package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface UserDao {

    User findByEmail(String email) throws SQLException;

    User findUserById(int id) throws SQLException;

    User registerUser(String email, String password, String name, String country, String city, String street, String zip, int money, boolean status) throws SQLException;

    void updateUser(User user) throws SQLException;

    void isRegistered(String email) throws SQLException;
}

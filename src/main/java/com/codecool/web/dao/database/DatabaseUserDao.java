package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.*;

public final class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User findUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users where user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return fetchUser(rs);
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        if (email == null || "".equals(email)) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public User registerUser(String email, String password, String name, String country, String city, String street, String zip, int money, boolean status) throws SQLException {
        String sql = "INSERT INTO users (email, password, name, country, city, street, zip_code, money, status) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setString(4, country);
            statement.setString(5, city);
            statement.setString(6, street);
            statement.setString(7, zip);
            statement.setInt(8, money);
            statement.setBoolean(9, status);

            executeInsert(statement);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return fetchUser(rs);
            }
        } catch (SQLException e) {
            throw e;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void updateUser(int id, String email, String name, String country, String city, String street, String zip, int money) throws SQLException {
        String sql = "UPDATE users SET (email, name, country, city, street, zip_code, money) = (?, ?, ?, ?, ?, ?, ?) WHERE user_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, country);
            ps.setString(4, city);
            ps.setString(5, street);
            ps.setString(6, zip);
            ps.setInt(7, money);
            ps.setInt(8, id);

            ps.executeUpdate();
        }
    }

    @Override
    public void updateUserMoney(int id, int money) throws SQLException {
        String sql = "UPDATE users SET (money) = (?) WHERE user_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, money);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

    @Override
    public void isRegistered(String email) throws SQLException {
        String sql = "SELECT email from users where email = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                throw new SQLException("Already Registered Mail Address!");
            }
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        String zipcode = resultSet.getString("zip_code");
        int money = resultSet.getInt("money");
        boolean status = resultSet.getBoolean("status");

        return new User(id, email, password, name, country, city, street, zipcode, money, status);
    }

}

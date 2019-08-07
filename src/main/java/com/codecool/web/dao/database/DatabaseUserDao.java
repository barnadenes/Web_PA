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
    public void registerUser(User user) throws SQLException {
        isRegistered(user.getEmail());
        String sql = "INSERT INTO users VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getCountry());
            statement.setString(6, user.getCity());
            statement.setString(7, user.getStreet());
            statement.setString(8, user.getZipcode());
            statement.setInt(9, user.setMoney(20));
            statement.setBoolean(10, user.setStatus(false));

            executeInsert(statement);
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET (email, name, country, city, street, zip_code, money,                            status) = (?, ?, ?, ?, ?, ?, ?) WHERE user_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getStreet());
            ps.setString(6, user.getZipcode());
            ps.setInt(7, user.getMoney());

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
            else {
                throw new SQLException("Haven't Registered Yet!");
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

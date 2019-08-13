package com.codecool.web.dao.database;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOrderDao extends AbstractDao implements OrderDao {

    public DatabaseOrderDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try(Statement ps = connection.createStatement();
            ResultSet rs = ps.executeQuery(sql)) {
            while(rs.next()){
                orders.add(fetchCheckout(rs));
            }
        }
        return orders;
    }

    public void addToOrders(String title, String buyer, int price) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO orders(book_title, buyer, price) " +
            "VALUES(?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, buyer);
            ps.setInt(3, price);
            executeInsert(ps);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autocommit);
        }
    }


    private Order fetchCheckout(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        String title = rs.getString("book_title");
        String buyer = rs.getString("buyer");
        int price = rs.getInt("price");
        return new Order(id, title, buyer, price);
    }
}

package com.codecool.web.dao;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> getAllOrders() throws SQLException;

    void addToOrders(String title, String buyer, int price) throws SQLException;
}

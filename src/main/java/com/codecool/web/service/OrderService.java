package com.codecool.web.service;

import com.codecool.web.model.Order;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    List<Order> findAllOrder() throws SQLException, ServiceException;

    void addToOrders(String title, String buyer, int price) throws SQLException;
}

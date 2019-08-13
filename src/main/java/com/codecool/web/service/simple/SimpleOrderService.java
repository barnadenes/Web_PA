package com.codecool.web.service.simple;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleOrderService implements OrderService {
    private final OrderDao orderDao;

    public SimpleOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAllOrder() throws SQLException, ServiceException {
        List<Order> orders = orderDao.getAllOrders();
        if(!orders.isEmpty()) {
            return orderDao.getAllOrders();
        }
        else {
            throw new ServiceException("No orders Yet!");
        }

    }

    @Override
    public void addToOrders(String title, String buyer, int price) throws SQLException {
        orderDao.addToOrders(title, buyer, price);
    }
}

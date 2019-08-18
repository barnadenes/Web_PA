package com.codecool.web.service;

import com.codecool.web.model.Checkout;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutService {

    Integer addToCart(String title, String buyer, String price, int userId) throws SQLException, ServiceException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException, ServiceException;

    Checkout findCheckoutById(String checkoutId) throws SQLException, ServiceException;

    void deleteCheckout(int userId ,String checkoutId, String buyer) throws SQLException;

    void deleteMainCheckout(int checkout_id, String buyer) throws SQLException;

    void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException;

    boolean inCart(String title, String buyer) throws SQLException;
}

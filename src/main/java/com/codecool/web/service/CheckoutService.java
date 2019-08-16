package com.codecool.web.service;

import com.codecool.web.model.Checkout;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutService {

    void addToCart(String title, String buyer, int price, int userId, String checkoutId) throws SQLException, ServiceException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException, ServiceException;

    Checkout findCheckoutById(String checkoutId) throws SQLException, ServiceException;

    void deleteCheckout(int userId ,String checkoutId) throws SQLException;

    void deleteMainCheckout(int checkoutId) throws SQLException;

    void addToUserCheckoutTable(int userId, String checkoutId) throws SQLException;

    boolean inCart(int userId, int checkoutId) throws SQLException;
}

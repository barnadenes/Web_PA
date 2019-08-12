package com.codecool.web.service;

import com.codecool.web.model.Checkout;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutService {

    void addToCart(Checkout item) throws SQLException;

    List<Checkout> findAllCheckout() throws SQLException, ServiceException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException, ServiceException;

    void deleteCheckout(int userId ,int checkoutId) throws SQLException;

    void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException, ServiceException;

    boolean inCart(int userId, int checkoutId) throws SQLException;
}

package com.codecool.web.dao;

import com.codecool.web.model.Checkout;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutDao {

    void addToCart(Checkout item) throws SQLException;

    List<Checkout> findAllCheckout() throws SQLException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException;

    void deleteCheckout(int checkoutId) throws SQLException;

    void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException;
}

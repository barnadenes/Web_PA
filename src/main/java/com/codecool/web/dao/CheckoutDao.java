package com.codecool.web.dao;

import com.codecool.web.model.Checkout;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutDao {

    void addToCart(int id, String title, String buyer, int price) throws SQLException;

    List<Checkout> findAllCheckout() throws SQLException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException;

    Checkout findCheckoutById(int checkout_id) throws SQLException;

    void deleteCheckout(int userId ,int checkoutId) throws SQLException;

    void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException;

    boolean inCart(int userId, int checkoutId) throws SQLException;
}

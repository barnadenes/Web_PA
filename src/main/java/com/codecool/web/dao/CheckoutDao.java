package com.codecool.web.dao;

import com.codecool.web.model.Checkout;

import java.sql.SQLException;
import java.util.List;

public interface CheckoutDao {

    Integer addToCart(String title, String buyer, int price) throws SQLException;

    List<Checkout> findCheckoutByUser(int userId) throws SQLException;

    Checkout findCheckoutById(int checkout_id) throws SQLException;

    void deleteCheckout(int userId ,int checkoutId) throws SQLException;

    void deleteCheckoutMain(int checkout_id, String buyer) throws SQLException;

    void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException;

    boolean inCart(String title, String buyer) throws SQLException;
}

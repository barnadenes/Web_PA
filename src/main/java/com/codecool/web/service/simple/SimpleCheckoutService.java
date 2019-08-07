package com.codecool.web.service.simple;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.model.Checkout;
import com.codecool.web.service.CheckoutService;

import java.sql.SQLException;
import java.util.List;

public final class SimpleCheckoutService implements CheckoutService {

    private final CheckoutDao checkoutDao;

    public SimpleCheckoutService(CheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }

    @Override
    public void addToCart(Checkout item) throws SQLException {
        
    }

    @Override
    public List<Checkout> findAllCheckout() throws SQLException {
        return null;
    }

    @Override
    public List<Checkout> findCheckoutByUser(int userId) throws SQLException {
        return null;
    }

    @Override
    public void deleteCheckout(int userId, int checkoutId) throws SQLException {

    }

    @Override
    public void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException {

    }

    @Override
    public boolean inCart(int userId, int checkoutId) throws SQLException {
        return false;
    }
}

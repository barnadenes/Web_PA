package com.codecool.web.service.simple;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.model.Checkout;
import com.codecool.web.service.CheckoutService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleCheckoutService implements CheckoutService {

    private final CheckoutDao checkoutDao;

    public SimpleCheckoutService(CheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }

    @Override
    public void addToCart(Checkout item) throws SQLException {
        checkoutDao.addToCart(item);
    }

    @Override
    public List<Checkout> findAllCheckout() throws SQLException, ServiceException {
        return checkoutDao.findAllCheckout();
    }

    @Override
    public List<Checkout> findCheckoutByUser(int userId) throws SQLException, ServiceException {
        List<Checkout> list = checkoutDao.findCheckoutByUser(userId);

        if(list.isEmpty()) {
            throw new ServiceException("Your Cart is Empty!");
        }
        return list;
    }

    @Override
    public void deleteCheckout(int userId, int checkoutId) throws SQLException {
        checkoutDao.deleteCheckout(userId, checkoutId);
    }

    @Override
    public void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException, ServiceException {
        if(inCart(userId, checkoutId)) {
            checkoutDao.addToUserCheckoutTable(userId, checkoutId);
        }
        else {
            throw new ServiceException("Already Purchased! / In Cart!");
        }

    }

    @Override
    public boolean inCart(int userId, int checkoutId) throws SQLException {
        return checkoutDao.inCart(userId, checkoutId);
    }
}

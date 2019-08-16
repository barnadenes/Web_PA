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
    public void addToCart(String title, String buyer, int price, int userId, String checkoutId) throws SQLException, ServiceException {
        int id = Integer.parseInt(checkoutId);

        if(!inCart(userId, id)) {
            checkoutDao.addToCart(id, title, buyer, price);
        }
        else {
            throw new ServiceException("Already Purchased! / In Cart!");
        }
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
    public Checkout findCheckoutById(String checkoutId) throws SQLException, ServiceException {
        Checkout checkout = checkoutDao.findCheckoutById(Integer.parseInt(checkoutId));

        if(checkout != null) {
            return checkout;
        }
        else {
            throw new ServiceException("Item was not Found!");
        }
    }

    @Override
    public void deleteCheckout(int userId, String checkoutId) throws SQLException {
        int checkoutID = Integer.parseInt(checkoutId);

        checkoutDao.deleteCheckout(userId, checkoutID);
        deleteMainCheckout(checkoutID);
    }

    @Override
    public void deleteMainCheckout(int checkoutId) throws SQLException {
        checkoutDao.deleteCheckoutMain(checkoutId);
    }

    @Override
    public void addToUserCheckoutTable(int userId, String checkoutId) throws SQLException {
        checkoutDao.addToUserCheckoutTable(userId, Integer.parseInt(checkoutId));
    }

    @Override
    public boolean inCart(int userId, int checkoutId) throws SQLException {
        return checkoutDao.inCart(userId, checkoutId);
    }
}

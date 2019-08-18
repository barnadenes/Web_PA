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
    public Integer addToCart(String title, String buyer, String price, int userId) throws SQLException, ServiceException {
        if(inCart(title, buyer)) {
            throw new ServiceException("Already In Cart!");
        }
        return checkoutDao.addToCart(title, buyer, Integer.valueOf(price));
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
    public void deleteCheckout(int userId, String checkoutId, String buyer) throws SQLException {
        int checkoutID = Integer.parseInt(checkoutId);

        checkoutDao.deleteCheckout(userId, checkoutID);
        deleteMainCheckout(checkoutID, buyer);
    }

    @Override
    public void deleteMainCheckout(int checkout_id, String buyer) throws SQLException {
        checkoutDao.deleteCheckoutMain(checkout_id, buyer);
    }

    @Override
    public void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException {
        checkoutDao.addToUserCheckoutTable(userId, checkoutId);
    }

    @Override
    public boolean inCart(String title, String buyer) throws SQLException {
        return checkoutDao.inCart(title, buyer);
    }
}

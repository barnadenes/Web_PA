package com.codecool.web.servlet;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.dao.ItemsDao;
import com.codecool.web.dao.database.DatabaseCheckoutDao;
import com.codecool.web.dao.database.DatabaseItemsDao;
import com.codecool.web.model.Checkout;
import com.codecool.web.model.ShopItems;
import com.codecool.web.model.User;
import com.codecool.web.service.CheckoutService;
import com.codecool.web.service.ItemsService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCheckoutService;
import com.codecool.web.service.simple.SimpleItemsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cart")
public class CheckoutServlet extends AbstractServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection = getConnection(req.getServletContext());
            CheckoutDao checkoutDao = new DatabaseCheckoutDao(connection);
            CheckoutService checkoutService = new SimpleCheckoutService(checkoutDao);
            User user = (User) req.getSession().getAttribute("user");

            List<Checkout> cart = checkoutService.findCheckoutByUser(user.getId());

            sendMessage(resp, HttpServletResponse.SC_OK, cart);
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection = getConnection(req.getServletContext());
            CheckoutDao checkoutDao = new DatabaseCheckoutDao(connection);
            CheckoutService checkoutService = new SimpleCheckoutService(checkoutDao);

            ItemsDao itemsDao = new DatabaseItemsDao(connection);
            ItemsService itemsService = new SimpleItemsService(itemsDao);


            String itemId = req.getParameter("item_id");
            User user = (User) req.getSession().getAttribute("user");
            ShopItems shopItems = itemsService.findItemById(itemId);

            int userId = user.getId();
            String title = shopItems.getTitle();
            String buyer = user.getEmail();
            int price = shopItems.getPrice();

            checkoutService.addToCart(title, buyer, price, userId, itemId);
            checkoutService.addToUserCheckoutTable(userId, itemId);


            sendMessage(resp, HttpServletResponse.SC_OK, "");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection = getConnection(req.getServletContext());
            CheckoutDao checkoutDao = new DatabaseCheckoutDao(connection);
            CheckoutService checkoutService = new SimpleCheckoutService(checkoutDao);

            User user = (User) req.getSession().getAttribute("user");
            String itemId = req.getParameter("delete_id");

            checkoutService.deleteCheckout(user.getId(), itemId);

            sendMessage(resp, HttpServletResponse.SC_OK, "");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        }
    }
}

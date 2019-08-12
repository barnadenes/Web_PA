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

import javax.servlet.ServletException;
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
        } catch (SQLException | ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
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

            checkoutService.addToUserCheckoutTable(userId, Integer.valueOf(itemId));
            checkoutService.addToCart(new Checkout(Integer.valueOf(itemId), title, buyer, price));

            sendMessage(resp, HttpServletResponse.SC_OK, "");
        } catch (SQLException | ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

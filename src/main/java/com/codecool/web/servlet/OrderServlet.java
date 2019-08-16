package com.codecool.web.servlet;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.dao.ItemsDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseCheckoutDao;
import com.codecool.web.dao.database.DatabaseItemsDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Checkout;
import com.codecool.web.model.Order;
import com.codecool.web.model.User;
import com.codecool.web.service.CheckoutService;
import com.codecool.web.service.ItemsService;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCheckoutService;
import com.codecool.web.service.simple.SimpleItemsService;
import com.codecool.web.service.simple.SimpleOrderService;
import com.codecool.web.service.simple.SimpleUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection = getConnection(req.getServletContext());
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService  orderService = new SimpleOrderService(orderDao);

            List<Order> orders = orderService.findAllOrder();

            sendMessage(resp, HttpServletResponse.SC_OK, orders);
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection  = getConnection(req.getServletContext());
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService  orderService = new SimpleOrderService(orderDao);

            CheckoutDao checkoutDao = new DatabaseCheckoutDao(connection);
            CheckoutService checkoutService = new SimpleCheckoutService(checkoutDao);

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            User sessionUser = (User)req.getSession().getAttribute("user");
            User user = userService.findUserById(sessionUser.getId());

            String checkout_id = req.getParameter("item_id");
            Checkout item = checkoutService.findCheckoutById(checkout_id);

            userService.updateUserMoney(user, item);
            orderService.addToOrders(item.getBookTitle(), item.getBuyer(), item.getPrice());

            sendMessage(resp, HttpServletResponse.SC_OK, "");
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection = getConnection(req.getServletContext());
            ItemsDao itemsDao = new DatabaseItemsDao(connection);
            ItemsService itemsService = new SimpleItemsService(itemsDao);

            String title = req.getParameter("title");
            String author = req.getParameter("author");
            String url = req.getParameter("url");
            String plot = req.getParameter("plot");
            String price = req.getParameter("price");

            itemsService.addItem(title, author, url, plot, price);

            sendMessage(resp, HttpServletResponse.SC_OK, "");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
            e.printStackTrace();
        }
    }
}

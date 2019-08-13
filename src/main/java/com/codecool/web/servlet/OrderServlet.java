package com.codecool.web.servlet;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.database.DatabaseCheckoutDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.model.Checkout;
import com.codecool.web.model.Order;
import com.codecool.web.service.CheckoutService;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCheckoutService;
import com.codecool.web.service.simple.SimpleOrderService;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {Connection connection  = getConnection(req.getServletContext());
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService  orderService = new SimpleOrderService(orderDao);

            CheckoutDao checkoutDao = new DatabaseCheckoutDao(connection);
            CheckoutService checkoutService = new SimpleCheckoutService(checkoutDao);

            String checkout_id = req.getParameter("checkout_id");
            Checkout item = checkoutService.findCheckoutById(Integer.parseInt(checkout_id));

            orderService.addToOrders(item.getBookTitle(), item.getBuyer(), item.getPrice());

            sendMessage(resp, HttpServletResponse.SC_OK, "Order has been Completed!");
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }
}

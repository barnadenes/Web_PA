package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/userInfo")
public class UserServlet extends AbstractServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Connection connection = getConnection(req.getServletContext());
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            User current = (User) req.getSession().getAttribute("user");
            int id = current.getId();

            String email = req.getParameter("email");
            String name = req.getParameter("name");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String street = req.getParameter("street");
            String money = req.getParameter("money");
            String zip = req.getParameter("zip");

            User user = userService.updateUser(id, email, name, country, city, street, zip, money);

            sendMessage(resp, HttpServletResponse.SC_OK, userService.findUserById(user.getId()));
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Connection connection = getConnection(req.getServletContext());
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            User current = (User) req.getSession().getAttribute("user");

            User user = userService.findUserById(current.getId());

            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e);
            e.printStackTrace();
        }
    }
}

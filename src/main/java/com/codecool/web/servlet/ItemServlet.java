package com.codecool.web.servlet;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.dao.database.DatabaseItemsDao;
import com.codecool.web.model.ShopItems;
import com.codecool.web.service.ItemsService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleItemsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/item")
public class ItemServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = getConnection(req.getServletContext());
            ItemsDao itemsDao = new DatabaseItemsDao(connection);
            ItemsService itemsService = new SimpleItemsService(itemsDao);
            String id = req.getParameter("id");

            ShopItems item = itemsService.findItemById(id);

            sendMessage(resp, HttpServletResponse.SC_OK, item);
        } catch (SQLException | ServiceException e) {
            handleSqlError(resp, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}

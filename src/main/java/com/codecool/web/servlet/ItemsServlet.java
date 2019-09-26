package com.codecool.web.servlet;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.dao.database.DatabaseItemsDao;
import com.codecool.web.model.ShopItem;
import com.codecool.web.service.ItemsService;
import com.codecool.web.service.simple.SimpleItemsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/items")
public class ItemsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext()))
        {
            ItemsDao itemsDao = new DatabaseItemsDao(connection);
            ItemsService itemsService = new SimpleItemsService(itemsDao);

            List<ShopItem> items = itemsService.findAllItem();

            sendMessage(resp, HttpServletResponse.SC_OK, items);
        } catch (SQLException e) {
            handleSqlError(resp, e);
            e.printStackTrace();
        }
    }
}

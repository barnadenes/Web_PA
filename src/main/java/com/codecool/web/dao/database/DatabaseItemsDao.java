package com.codecool.web.dao.database;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseItemsDao extends AbstractDao implements ItemsDao {

    DatabaseItemsDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Item> findAllItem() throws SQLException {
        List<Item> itemList = new ArrayList<>();
        String sql = "select * from items";

        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                itemList.add(fetchItem(rs));
            }
        }
        return itemList;
    }

    private Item fetchItem(ResultSet rs) throws SQLException {
        int id = rs.getInt("item_id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String url = rs.getString("url");
        String plot = rs.getString("plot");
        int price = rs.getInt("price");
        return new Item(id, title, author, url, plot, price);
    }
}

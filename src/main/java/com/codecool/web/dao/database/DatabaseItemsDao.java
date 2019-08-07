package com.codecool.web.dao.database;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseItemsDao extends AbstractDao implements ItemsDao {

    DatabaseItemsDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<ShopItem> findAllItem() throws SQLException {
        List<ShopItem> itemList = new ArrayList<>();
        String sql = "select item_id, title, url from items";

        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                itemList.add(fetchItem(rs));
            }
        }
        return itemList;
    }

    @Override
    public ShopItems findItemById(int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return fetchItems(rs);
            }
        }
        return null;
    }

    @Override
    public void addItem(ShopItem item) throws SQLException {

    }

    private ShopItems fetchItems(ResultSet rs) throws SQLException {
        int id = rs.getInt("item_id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String url = rs.getString("url");
        String plot = rs.getString("plot");
        int price = rs.getInt("price");
        return new ShopItems(id, title, author, url, plot, price);
    }

    private ShopItem fetchItem(ResultSet rs) throws SQLException {
        int id = rs.getInt("item_id");
        String title = rs.getString("title");
        String url = rs.getString("url");
        return new ShopItem(id, title, url);
    }
}

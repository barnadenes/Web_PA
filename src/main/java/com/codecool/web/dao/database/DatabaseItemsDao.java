package com.codecool.web.dao.database;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseItemsDao extends AbstractDao implements ItemsDao {

    public DatabaseItemsDao(Connection connection) {
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
    public void addItem(String title, String author, String url, String plot, int price) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO items(title, author, url, plot, price) " +
            "VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, url);
            ps.setString(4, plot);
            ps.setInt(5, price);
            executeInsert(ps);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autocommit);
        }
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

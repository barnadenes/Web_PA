package com.codecool.web.dao;

import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;

import java.sql.SQLException;
import java.util.List;

public interface ItemsDao {

    List<ShopItem> findAllItem() throws SQLException;

    ShopItems findItemById(int id) throws SQLException;

    void addItem(ShopItem item) throws SQLException;
}

package com.codecool.web.service;

import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;

import java.sql.SQLException;
import java.util.List;

public interface ItemsService {

    List<ShopItem> findAllItem() throws SQLException;

    ShopItems findItemById(int id) throws SQLException;

    void addItem(ShopItem item) throws SQLException;
}

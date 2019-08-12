package com.codecool.web.service;

import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface ItemsService {

    List<ShopItem> findAllItem() throws SQLException;

    ShopItems findItemById(String id) throws SQLException, ServiceException;

    void addItem(ShopItem item) throws SQLException;
}

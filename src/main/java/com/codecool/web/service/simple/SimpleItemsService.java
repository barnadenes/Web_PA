package com.codecool.web.service.simple;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;
import com.codecool.web.service.ItemsService;

import java.sql.SQLException;
import java.util.List;

public final class SimpleItemsService implements ItemsService {

    private final ItemsDao itemsDao;

    public SimpleItemsService(ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public List<ShopItem> findAllItem() throws SQLException {
        return null;
    }

    @Override
    public ShopItems findItemById(int id) throws SQLException {
        return null;
    }

    @Override
    public void addItem(ShopItem item) throws SQLException {

    }
}

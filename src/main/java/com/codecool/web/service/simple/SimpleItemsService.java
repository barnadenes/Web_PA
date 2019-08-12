package com.codecool.web.service.simple;

import com.codecool.web.dao.ItemsDao;
import com.codecool.web.model.ShopItem;
import com.codecool.web.model.ShopItems;
import com.codecool.web.service.ItemsService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleItemsService implements ItemsService {

    private final ItemsDao itemsDao;

    public SimpleItemsService(ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public List<ShopItem> findAllItem() throws SQLException {
        return itemsDao.findAllItem();
    }

    @Override
    public ShopItems findItemById(String id) throws SQLException, ServiceException {
        try{
            return itemsDao.findItemById(Integer.valueOf(id));
        }
        catch (NumberFormatException e) {
            throw new ServiceException(e.getMessage() + "Item id should be an Integer!");
        }

    }

    @Override
    public void addItem(ShopItem item) throws SQLException {

    }
}

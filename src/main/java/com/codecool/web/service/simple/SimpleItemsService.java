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
    public void addItem(String title, String author, String url, String plot, String price) throws SQLException, ServiceException {
        try {
            if(title.isEmpty() | author.isEmpty() | url.isEmpty() | plot.isEmpty() | price.isEmpty()) {
                throw new ServiceException("You have to fill all fields!");
            }
            else if(Integer.parseInt(price) <= 0) {
                throw new ServiceException("Price should be more than 0!");
            }
            itemsDao.addItem(title, author, url, plot, Integer.parseInt(price));
        } catch (NumberFormatException e) {
            throw new ServiceException("Price should be a Number!");
        }
    }
}

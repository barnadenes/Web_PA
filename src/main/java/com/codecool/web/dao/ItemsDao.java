package com.codecool.web.dao;

import com.codecool.web.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemsDao {

    List<Item> findAllItem() throws SQLException;
}

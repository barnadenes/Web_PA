package com.codecool.web.dao;

import com.codecool.web.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface itemsDao {

    List<Item> findAllItem() throws SQLException;
}

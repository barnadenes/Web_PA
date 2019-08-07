package com.codecool.web.model;

public class ShopItem extends AbstractModel {
    private final String title;
    private final String url;

    public ShopItem(int id, String title, String url) {
        super(id);
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}

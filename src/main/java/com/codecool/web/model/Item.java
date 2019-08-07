package com.codecool.web.model;

public final class Item extends AbstractModel{
    private final String url;
    private final String title;

    public Item(int id, String url, String title) {
        super(id);
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}

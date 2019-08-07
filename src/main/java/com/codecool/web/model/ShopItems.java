package com.codecool.web.model;

public final class ShopItems extends AbstractModel{
    private final String title;
    private final String author;
    private final String url;
    private final String plot;
    private final int price;

    public ShopItems(int id, String title, String author, String url, String plot, int price) {
        super(id);
        this.title = title;
        this.author = author;
        this.url = url;
        this.plot = plot;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getPlot() {
        return plot;
    }

    public int getPrice() {
        return price;
    }

}

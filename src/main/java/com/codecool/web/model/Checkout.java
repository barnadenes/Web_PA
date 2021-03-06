package com.codecool.web.model;

public final class Checkout extends AbstractModel{
    private final String bookTitle;
    private final String buyer;
    private final int price;

    public Checkout(int id, String bookTitle, String buyer, int price) {
        super(id);
        this.bookTitle = bookTitle;
        this.buyer = buyer;
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBuyer() {
        return buyer;
    }

    public int getPrice() {
        return price;
    }
}

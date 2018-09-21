package com.devcamilla.shoppingcart.models;

public class Item extends Entity {
    private String code;

    private double price;

    protected Item() {}

    public Item(String code, double price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }
}

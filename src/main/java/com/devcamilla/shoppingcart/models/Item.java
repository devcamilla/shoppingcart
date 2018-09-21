package com.devcamilla.shoppingcart.models;

import java.util.UUID;

public class Item {
    private UUID id;

    private String code;

    private double price;

    protected Item() {}

    public Item(String code, double price){
        this.id = UUID.randomUUID();
        this.code = code;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }
}

package com.devcamilla.shoppingcart.models;

import java.util.UUID;

public abstract class Entity {
    private UUID id;

    protected  Entity(){
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}

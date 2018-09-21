package com.devcamilla.shoppingcart.repositories;

import com.devcamilla.shoppingcart.models.Item;
import com.devcamilla.shoppingcart.settings.DatasourceSettings;

public class ItemRepository extends Repository<Item>{
    public ItemRepository(DatasourceSettings datasourceSettings) {
        super(datasourceSettings, Item.class, "items");
    }
}

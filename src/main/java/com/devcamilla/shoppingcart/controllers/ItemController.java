package com.devcamilla.shoppingcart.controllers;

import com.devcamilla.shoppingcart.models.Item;
import com.devcamilla.shoppingcart.repositories.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @PostMapping()
    public UUID create(@RequestParam String code, @RequestParam double price){
        Item item = new Item(code, price);
        ItemRepository itemRepository = new ItemRepository();
        itemRepository.save(item);

        return item.getId();
    }

    @GetMapping
    public List<Item> getAll(){
        ItemRepository itemRepository = new ItemRepository();
        return itemRepository.get();
    }

    @GetMapping("/{id}")
    public Optional<Item> get(@PathVariable UUID id){
        ItemRepository itemRepository =  new ItemRepository();
        return itemRepository.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        ItemRepository itemRepository = new ItemRepository();
        itemRepository.delete(id);
    }
}

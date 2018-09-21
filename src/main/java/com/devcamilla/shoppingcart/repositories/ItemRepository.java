package com.devcamilla.shoppingcart.repositories;

import com.devcamilla.shoppingcart.models.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ItemRepository {
    private final String url = "jdbc:postgresql://localhost:5432/shoppingcart";
    private final String user = "postgres";
    private final String password = "admin";

    public void save(Item item){
        String sql = "insert into items (id, content, timestamp) values (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            Date date = new java.util.Date();
            Timestamp timestampNow = new Timestamp(date.getTime());

            ObjectMapper objMapper =  new ObjectMapper();
            String jsonString = objMapper.writeValueAsString(item);

            preparedStatement.setObject(1, item.getId());
            preparedStatement.setObject(2, jsonString);
            preparedStatement.setObject(3, timestampNow);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Item> get(){
        String sql = "select * from items";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            List<Item> entities = new ArrayList<>();
            while (result.next()) {
                String content = result.getString("content");

                ObjectMapper objectMapper = new ObjectMapper();
                Item item = objectMapper.readValue(content, Item.class);
                entities.add(item);
            }

            return entities;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void delete(UUID id) {
        String sql = "delete from items where id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Item> get(UUID id) {
        String sql = "select * from items where id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()){
                String content = result.getString("content");

                ObjectMapper objectMapper = new ObjectMapper();
                Item item = objectMapper.readValue(content, Item.class);
                return Optional.ofNullable(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

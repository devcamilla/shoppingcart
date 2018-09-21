package com.devcamilla.shoppingcart.repositories;

import com.devcamilla.shoppingcart.models.Entity;
import com.devcamilla.shoppingcart.settings.DatasourceSettings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class Repository <T extends Entity> {
    private DatasourceSettings datasourceSettings;
    private final Class<T> type;
    private final String tableName;

    public Repository(DatasourceSettings datasourceSettings, Class<T> type, String tableName){
        this.datasourceSettings = datasourceSettings;
        this.type = type;
        this.tableName = tableName;
    }

    public void save(T entity){
        String sql = "insert into " + this.tableName + " (id, content, timestamp) values (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(datasourceSettings.getUrl(), datasourceSettings.getUsername(), datasourceSettings.getPassword()); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            Date date = new java.util.Date();
            Timestamp timestampNow = new Timestamp(date.getTime());

            ObjectMapper objMapper =  new ObjectMapper();
            String jsonString = objMapper.writeValueAsString(entity);

            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setObject(2, jsonString);
            preparedStatement.setObject(3, timestampNow);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<T> get(){
        String sql = "select * from " + this.tableName;

        try (Connection con = DriverManager.getConnection(datasourceSettings.getUrl(), datasourceSettings.getUsername(), datasourceSettings.getPassword()); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (result.next()) {
                String content = result.getString("content");

                ObjectMapper objectMapper = new ObjectMapper();
                T entity = objectMapper.readValue(content, type);

                entities.add(entity);
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
        String sql = "delete from " + this.tableName + " where id = ?";

        try (Connection con = DriverManager.getConnection(datasourceSettings.getUrl(), datasourceSettings.getUsername(), datasourceSettings.getPassword()); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<T> get(UUID id) {
        String sql = "select * from "+ this.tableName +" where id = ?";

        try (Connection con = DriverManager.getConnection(datasourceSettings.getUrl(), datasourceSettings.getUsername(), datasourceSettings.getPassword()); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()){
                String content = result.getString("content");

                ObjectMapper objectMapper = new ObjectMapper();
                return Optional.of(objectMapper.readValue(content, type));
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

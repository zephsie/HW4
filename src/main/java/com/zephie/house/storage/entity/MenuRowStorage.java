package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.core.entity.MenuRow;
import com.zephie.house.core.entity.Pizza;
import com.zephie.house.core.entity.PizzaInfo;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.util.exceptions.FKNotFound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MenuRowStorage implements IMenuRowStorage {
    private static volatile MenuRowStorage instance;
    private final Connection connection;

    private MenuRowStorage() {
        try {
            connection = DataSourceInitializer.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while connecting to DB");
        }
    }

    @Override
    public Optional<IMenuRow> read(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT menu_row.id, pizza_info.id pizza_info_id, pizza.id pizza_id, name, description, size, price\n" +
                            "FROM structure.pizza_info\n" +
                            "JOIN structure.pizza ON structure.pizza_info.pizza = structure.pizza.id\n" +
                            "JOIN structure.menu_row ON structure.menu_row.pizza_info = structure.pizza_info.id\n" +
                            "WHERE menu_row.id = ?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new MenuRow(resultSet.getLong("id"),
                        new PizzaInfo(resultSet.getLong("pizza_info_id"),
                                resultSet.getInt("size"),
                                new Pizza(resultSet.getLong("pizza_id"), resultSet.getString("name"), resultSet.getString("description"))),
                        resultSet.getInt("price")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading MenuRow");
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM structure.menu_row WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting MenuRow");
        }
    }

    @Override
    public Collection<IMenuRow> get() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT menu_row.id, pizza_info.id pizza_info_id, pizza.id pizza_id, name, description, size, price\n" +
                            "FROM structure.pizza_info\n" +
                            "JOIN structure.pizza ON structure.pizza_info.pizza = structure.pizza.id\n" +
                            "JOIN structure.menu_row ON structure.menu_row.pizza_info = structure.pizza_info.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<IMenuRow> menuRows = new ArrayList<>();

            while (resultSet.next()) {
                menuRows.add(new MenuRow(resultSet.getLong("id"),
                        new PizzaInfo(resultSet.getLong("pizza_info_id"),
                                resultSet.getInt("size"),
                                new Pizza(resultSet.getLong("pizza_id"), resultSet.getString("name"), resultSet.getString("description"))),
                        resultSet.getInt("price")));
            }

            return menuRows;

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading MenuRow");
        }
    }

    @Override
    public void create(MenuRowDTO menuRow) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO structure.menu_row (pizza_info, price) VALUES (?, ?)");

            preparedStatement.setLong(1, menuRow.getPizzaInfoId());
            preparedStatement.setDouble(2, menuRow.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                throw new FKNotFound("PizzaInfo with id " + menuRow.getPizzaInfoId() + " does not exist");
            }

            throw new RuntimeException("Something went wrong while creating MenuRow");
        }


    }

    @Override
    public void update(Long id, MenuRowDTO menuRow) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE structure.menu_row SET pizza_info = ?, price = ? WHERE id = ?");

            preparedStatement.setLong(1, menuRow.getPizzaInfoId());
            preparedStatement.setDouble(2, menuRow.getPrice());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                throw new FKNotFound("PizzaInfo with id " + menuRow.getPizzaInfoId() + " does not exist");
            }

            throw new RuntimeException("Something went wrong while updating MenuRow");
        }
    }

    public static MenuRowStorage getInstance() {
        if (instance == null) {
            synchronized (MenuRowStorage.class) {
                if (instance == null) {
                    instance = new MenuRowStorage();
                }
            }
        }

        return instance;
    }
}

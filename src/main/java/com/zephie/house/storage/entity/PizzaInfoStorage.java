package com.zephie.house.storage.entity;

import com.zephie.house.core.entity.Pizza;
import com.zephie.house.core.entity.PizzaInfo;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.util.exceptions.FKNotFound;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PizzaInfoStorage implements IPizzaInfoStorage {
    private static volatile PizzaInfoStorage instance;
    private final DataSource dataSource;

    private PizzaInfoStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }

    @Override
    public Optional<IPizzaInfo> read(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT pizza_info.id, pizza.id pizza_id, name, description, size\n" +
                            "FROM structure.pizza_info\n" +
                            "JOIN structure.pizza ON structure.pizza_info.pizza = structure.pizza.id\n" +
                            "WHERE pizza_info.id = ?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new PizzaInfo(resultSet.getLong("id"),
                        resultSet.getInt("size"),
                        new Pizza(resultSet.getLong("pizza_id"), resultSet.getString("name"), resultSet.getString("description"))));
            }


        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM structure.pizza_info WHERE id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Pizza");
        }
    }

    @Override
    public Collection<IPizzaInfo> get() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT pizza_info.id, pizza.id pizza_id, name, description, size\n" +
                            "FROM structure.pizza_info\n" +
                            "JOIN structure.pizza ON structure.pizza_info.pizza = structure.pizza.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<IPizzaInfo> pizzaInfos = new ArrayList<>();

            while (resultSet.next()) {
                pizzaInfos.add(new PizzaInfo(resultSet.getLong("id"),
                        resultSet.getInt("size"),
                        new Pizza(resultSet.getLong("pizza_id"), resultSet.getString("name"), resultSet.getString("description"))));
            }

            return pizzaInfos;

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }
    }

    @Override
    public void create(PizzaInfoDTO pizzaInfo) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO structure.pizza_info (pizza, size) VALUES (?, ?)");

            preparedStatement.setLong(1, pizzaInfo.getPizzaId());
            preparedStatement.setInt(2, pizzaInfo.getSize());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                throw new FKNotFound("Pizza with id " + pizzaInfo.getPizzaId() + " does not exist");
            }

            throw new RuntimeException("Something went wrong while creating Pizza");
        }
    }

    @Override
    public void update(Long id, PizzaInfoDTO pizzaInfo) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE structure.pizza_info SET pizza = ?, size = ? WHERE id = ?");

            preparedStatement.setLong(1, pizzaInfo.getPizzaId());
            preparedStatement.setInt(2, pizzaInfo.getSize());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                throw new FKNotFound("Pizza with id " + pizzaInfo.getPizzaId() + " does not exist");
            }

            throw new RuntimeException("Something went wrong while updating Pizza");
        }
    }

    public static PizzaInfoStorage getInstance() {
        if (instance == null) {
            synchronized (PizzaInfoStorage.class) {
                if (instance == null) {
                    instance = new PizzaInfoStorage();
                }
            }
        }

        return instance;
    }
}

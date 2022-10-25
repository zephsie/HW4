package com.zephie.house.storage.entity;

import com.zephie.house.core.entity.Pizza;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.storage.api.IPizzaStorage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PizzaStorage implements IPizzaStorage {

    private static volatile PizzaStorage instance;

    private final DataSource dataSource;

    private PizzaStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }

    @Override
    public Optional<IPizza> read(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description FROM structure.pizza WHERE id = ?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Pizza(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }

        return Optional.empty();
    }

    @Override
    public void create(PizzaDTO pizza) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO structure.pizza (name, description) VALUES (?, ?)");

            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setString(2, pizza.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new NotUniqueException("Pizza with this name already exists");
            }

            throw new RuntimeException("Something went wrong while creating Pizza");
        }
    }

    @Override
    public Collection<IPizza> get() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description FROM structure.pizza");

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<IPizza> pizzas = new ArrayList<>();

            while (resultSet.next()) {
                pizzas.add(new Pizza(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description")));
            }

            return pizzas;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting Pizza");
        }
    }

    @Override
    public void update(Long id, PizzaDTO pizza) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE structure.pizza SET name = ?, description = ? WHERE id = ?");

            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new NotUniqueException("Pizza with this name already exists");
            }

            throw new RuntimeException("Something went wrong while updating Pizza");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM structure.pizza WHERE id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Pizza");
        }
    }

    @Override
    public Optional<IPizza> read(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, description FROM structure.pizza WHERE name = ?");

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Pizza(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }

        return Optional.empty();
    }

    @Override
    public void update(String name, PizzaDTO pizza) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE structure.pizza SET name = ?, description = ? WHERE name = ?");

            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new NotUniqueException("Pizza with this name already exists");
            }

            throw new RuntimeException("Something went wrong while updating Pizza");
        }
    }

    @Override
    public void delete(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM structure.pizza WHERE name = ?");

            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Pizza");
        }
    }

    public static PizzaStorage getInstance() {
        if (instance == null) {
            synchronized (PizzaStorage.class) {
                if (instance == null) {
                    instance = new PizzaStorage();
                }
            }
        }

        return instance;
    }
}

package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.SystemPizzaDTO;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.util.mappers.ResultSetPizzaMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PizzaStorage implements IPizzaStorage {

    private static volatile PizzaStorage instance;

    private final DataSource dataSource;

    private static final String INSERT = "INSERT INTO structure.pizza (name, description, dt_create, dt_update) VALUES (?, ?, ?, ?)";
    private static final String SELECT = "SELECT id, name, description, dt_create, dt_update FROM structure.pizza";
    private static final String SELECT_BY_ID = "SELECT id, name, description, dt_create, dt_update FROM structure.pizza WHERE id = ?";
    private static final String UPDATE = "UPDATE structure.pizza SET name = ?, description = ?, dt_update = ? WHERE id = ? AND dt_update = ?";
    private static final String DELETE = "DELETE FROM structure.pizza WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id, name, description, dt_create, dt_update FROM structure.pizza WHERE name = ?";

    private PizzaStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }

    @Override
    public Optional<IPizza> read(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Optional.of(ResultSetPizzaMapper.map(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }
    }

    @Override
    public Collection<IPizza> read() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<IPizza> pizzas = new ArrayList<>();

            while (resultSet.next()) {
                pizzas.add(ResultSetPizzaMapper.map(resultSet));
            }

            return pizzas;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting Pizza");
        }
    }

    @Override
    public IPizza create(SystemPizzaDTO systemPizzaDTO) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setString(1, systemPizzaDTO.getName());
            preparedStatement.setString(2, systemPizzaDTO.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaDTO.getCreateDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemPizzaDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            return read(systemPizzaDTO.getName()).orElseThrow(() -> new RuntimeException("Something went wrong while getting created pizza"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating Pizza");
        }
    }

    @Override
    public IPizza update(Long id, SystemPizzaDTO systemPizzaDTO, LocalDateTime updateDate) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, systemPizzaDTO.getName());
            preparedStatement.setString(2, systemPizzaDTO.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaDTO.getUpdateDate()));

            preparedStatement.setLong(4, id);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(updateDate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pizza was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while getting updated pizza"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating Pizza");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pizza was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Pizza");
        }
    }

    @Override
    public Optional<IPizza> read(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Optional.of(ResultSetPizzaMapper.map(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
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
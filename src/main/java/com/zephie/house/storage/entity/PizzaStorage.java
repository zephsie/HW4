package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.SystemPizzaDTO;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.mappers.entity.ResultSetToPizzaMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class PizzaStorage implements IPizzaStorage {
    private final DataSource dataSource;

    private final ResultSetToPizzaMapper resultSetToPizzaMapper;

    private static final String INSERT = "INSERT INTO structure.pizza (name, description, dt_create, dt_update) VALUES (?, ?, ?, ?)";
    private static final String SELECT = "SELECT id pizza_id, name pizza_name FROM structure.pizza";
    private static final String SELECT_BY_ID = "SELECT id pizza_id, name pizza_name, description pizza_description, dt_create, dt_update FROM structure.pizza WHERE id = ?";
    private static final String UPDATE = "UPDATE structure.pizza SET name = ?, description = ?, dt_update = ? WHERE id = ? AND dt_update = ?";
    private static final String DELETE = "DELETE FROM structure.pizza WHERE id = ? AND dt_update = ?";
    private static final String SELECT_BY_NAME = "SELECT id pizza_id, name pizza_name, description pizza_description, dt_create, dt_update FROM structure.pizza WHERE name = ?";
    private static final String IS_EXIST = "SELECT EXISTS(SELECT 1 FROM structure.pizza WHERE id = ?)";

    public PizzaStorage(DataSource dataSource, ResultSetToPizzaMapper resultSetToPizzaMapper) {
        this.dataSource = dataSource;
        this.resultSetToPizzaMapper = resultSetToPizzaMapper;
    }

    @Override
    public Optional<IPizza> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(resultSetToPizzaMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }
    }

    @Override
    public Collection<IPizza> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                Collection<IPizza> pizzas = new HashSet<>();

                while (resultSet.next()) {
                    pizzas.add(resultSetToPizzaMapper.partialMap(resultSet));
                }

                return pizzas;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting Pizza");
        }
    }

    @Override
    public IPizza create(SystemPizzaDTO systemPizzaDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, systemPizzaDTO.getName());
            preparedStatement.setString(2, systemPizzaDTO.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaDTO.getCreateDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemPizzaDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created Pizza"));
                } else {
                    throw new RuntimeException("Keys were not generated");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating Pizza");
        }
    }

    @Override
    public IPizza update(Long id, SystemPizzaDTO systemPizzaDTO, LocalDateTime updateDate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, systemPizzaDTO.getName());
            preparedStatement.setString(2, systemPizzaDTO.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaDTO.getUpdateDate()));

            preparedStatement.setLong(4, id);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(updateDate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pizza was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while getting updated Pizza"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating Pizza");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime updateDate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(updateDate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pizza was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Pizza");
        }
    }

    @Override
    public boolean isPresent(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_EXIST)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking Pizza");
        }
    }

    @Override
    public Optional<IPizza> read(String name) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(resultSetToPizzaMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Pizza");
        }
    }
}
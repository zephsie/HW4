package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.dto.SystemPizzaInfoDTO;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.util.mappers.ResultSetToPizzaInfoMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PizzaInfoStorage implements IPizzaInfoStorage {
    private static volatile PizzaInfoStorage instance;
    private final DataSource dataSource;

    private static final String INSERT = "INSERT INTO structure.pizza_info (pizza_id, size, dt_create, dt_update) VALUES (?, ?, ?, ?)";

    private static final String SELECT = "SELECT pizza_info.id, pizza_id, name, description, pizza.dt_create pizza_dt_create, pizza.dt_update pizza_dt_update, size, pizza_info.dt_create, pizza_info.dt_update\n" +
            "\tFROM structure.pizza_info\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id";

    private static final String SELECT_BY_ID = "SELECT pizza_info.id, pizza_id, name, description, pizza.dt_create pizza_dt_create, pizza.dt_update pizza_dt_update, size, pizza_info.dt_create, pizza_info.dt_update\n" +
            "\tFROM structure.pizza_info\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tWHERE pizza_info.id = ?";

    private static final String UPDATE = "UPDATE structure.pizza_info SET pizza_id = ?, size = ?, dt_update = ? WHERE id = ? AND dt_update = ?";

    private static final String DELETE = "DELETE FROM structure.pizza_info WHERE id = ? AND dt_update = ?";

    private PizzaInfoStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }

    @Override
    public IPizzaInfo create(SystemPizzaInfoDTO systemPizzaInfoDTO) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, systemPizzaInfoDTO.getPizzaId());
            preparedStatement.setInt(2, systemPizzaInfoDTO.getSize());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaInfoDTO.getCreateDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemPizzaInfoDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created PizzaInfo  "));
                } else {
                    throw new RuntimeException("Something went wrong while creating PizzaInfo");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating PizzaInfo");
        }
    }

    @Override
    public Optional<IPizzaInfo> read(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Optional.of(ResultSetToPizzaInfoMapper.map(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading PizzaInfo");
        }
    }

    @Override
    public Collection<IPizzaInfo> read() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<IPizzaInfo> pizzaInfos = new ArrayList<>();

            while (resultSet.next()) {
                pizzaInfos.add(ResultSetToPizzaInfoMapper.map(resultSet));
            }

            return pizzaInfos;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading PizzaInfo");
        }
    }

    @Override
    public IPizzaInfo update(Long id, SystemPizzaInfoDTO systemPizzaInfoDTO, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setLong(1, systemPizzaInfoDTO.getPizzaId());
            preparedStatement.setInt(2, systemPizzaInfoDTO.getSize());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemPizzaInfoDTO.getUpdateDate()));

            preparedStatement.setLong(4, id);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("PizzaInfo was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while reading updated PizzaInfo"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating PizzaInfo");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, id);
            preparedStatement.setObject(2, dateUpdate);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("PizzaInfo was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting PizzaInfo");
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

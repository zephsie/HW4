package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.SystemMenuRowDTO;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.util.mappers.ResultSetToMenuRowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class MenuRowStorage implements IMenuRowStorage {
    private final DataSource dataSource;

    private static final String SELECT = "SELECT menu_row.id menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, menu_id, menu.name menu_name, price menu_row_price\n" +
            "\tFROM structure.menu_row\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tJOIN structure.menu ON menu_id = menu.id";

    private static final String SELECT_BY_ID = "SELECT menu_row.id menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, menu_id, menu.name menu_name, price menu_row_price, menu_row.dt_create, menu_row.dt_update\n" +
            "\tFROM structure.menu_row\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tJOIN structure.menu ON menu_id = menu.id\n" +
            "\tWHERE menu_row.id = ?";

    private static final String INSERT = "INSERT INTO structure.menu_row (pizza_info_id, menu_id, price, dt_create, dt_update) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE structure.menu_row SET pizza_info_id = ?, menu_id = ?, price = ?, dt_update = ? WHERE id = ? AND dt_update = ?";

    private static final String DELETE = "DELETE FROM structure.menu_row WHERE id = ? AND dt_update = ?";

    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM structure.menu_row WHERE id = ?)";

    private static final String CHECK_IF_EXISTS_AND_ACTIVE = "SELECT EXISTS(SELECT 1 FROM structure.menu_row JOIN structure.menu ON menu_id = menu.id WHERE menu_row.id = ? AND menu.enable = true)";

    public MenuRowStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IMenuRow create(SystemMenuRowDTO systemMenuRowDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, systemMenuRowDTO.getPizzaInfoId());
            preparedStatement.setLong(2, systemMenuRowDTO.getMenuId());
            preparedStatement.setDouble(3, systemMenuRowDTO.getPrice());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemMenuRowDTO.getCreateDate()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(systemMenuRowDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return read(resultSet.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created MenuRow"));
                } else {
                    throw new RuntimeException("Keys were not generated");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating MenuRow");
        }

    }

    @Override
    public Optional<IMenuRow> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToMenuRowMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while reading MenuRow)");
        }
    }

    @Override
    public Collection<IMenuRow> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Collection<IMenuRow> menuRows = new HashSet<>();

                while (resultSet.next()) {
                    menuRows.add(ResultSetToMenuRowMapper.partialMap(resultSet));
                }

                return menuRows;
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while reading MenuRow");
        }
    }

    @Override
    public IMenuRow update(Long id, SystemMenuRowDTO systemMenuRowDTO, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setLong(1, systemMenuRowDTO.getPizzaInfoId());
            preparedStatement.setLong(2, systemMenuRowDTO.getMenuId());
            preparedStatement.setDouble(3, systemMenuRowDTO.getPrice());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemMenuRowDTO.getUpdateDate()));

            preparedStatement.setLong(5, id);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Menu Row was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while reading updated MenuRow"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating MenuRow");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Menu Row was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting MenuRow");
        }
    }

    @Override
    public boolean isPresent(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_EXISTS)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking MenuRow");
        }
    }

    @Override
    public Boolean isAvailable(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_EXISTS_AND_ACTIVE)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking MenuRow");
        }
    }
}
package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.SystemMenuDTO;
import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.util.mappers.ResultSetToMenuMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MenuStorage implements IMenuStorage {
    private static final String SELECT = "SELECT id menu_id, name menu_name, enable menu_enable FROM structure.menu";

    private static final String SELECT_BY_ID = "SELECT id menu_id, name menu_name, enable menu_enable, dt_create, dt_update FROM structure.menu WHERE id = ?";

    private static final String SELECT_BY_NAME = "SELECT id menu_id, name menu_name, enable menu_enable, dt_create, dt_update FROM structure.menu WHERE name = ?";

    private static final String SELECT_MENU_ROWS_BY_MENU_ID = "SELECT menu_row.id menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, price menu_row_price\n" +
            "\tFROM structure.menu_row\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tWHERE menu_id = ?";

    private static final String INSERT = "INSERT INTO structure.menu (name, enable, dt_create, dt_update) VALUES (?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE structure.menu SET name = ?, enable = ?, dt_update = ? WHERE id = ? AND dt_update = ?";

    private static final String DELETE = "DELETE FROM structure.menu WHERE id = ? AND dt_update = ?";

    private final DataSource dataSource;

    public MenuStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IMenu create(SystemMenuDTO systemMenuDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, systemMenuDTO.getName());
            preparedStatement.setBoolean(2, systemMenuDTO.getActive());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemMenuDTO.getCreateDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemMenuDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created Menu"));
                } else {
                    throw new RuntimeException("Something went wrong while creating Menu");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating Menu");
        }
    }

    @Override
    public Optional<IMenu> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    IMenu menu = ResultSetToMenuMapper.fullMap(resultSet);
                    Set<IMenuRow> menuRows = new HashSet<>();

                    try (PreparedStatement statementToGetMenuRows = connection.prepareStatement(SELECT_MENU_ROWS_BY_MENU_ID)) {
                        statementToGetMenuRows.setLong(1, id);
                        try (ResultSet resultSetToGetMenuRows = statementToGetMenuRows.executeQuery()) {
                            while (resultSetToGetMenuRows.next()) {
                                menuRows.add(ResultSetToMenuMapper.fullMapRow(resultSetToGetMenuRows));
                            }
                        }
                    }

                    menu.setRows(menuRows);

                    return Optional.of(menu);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Menu");
        }
    }

    @Override
    public Optional<IMenu> read(String name) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToMenuMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Menu");
        }

    }

    @Override
    public Collection<IMenu> read() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(SELECT)) {
                Collection<IMenu> menuCollection = new HashSet<>();

                while (resultSet.next()) {
                    menuCollection.add(ResultSetToMenuMapper.partialMap(resultSet));
                }

                return menuCollection;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Menu");
        }
    }

    @Override
    public IMenu update(Long id, SystemMenuDTO systemMenuDTO, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, systemMenuDTO.getName());
            preparedStatement.setBoolean(2, systemMenuDTO.getActive());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemMenuDTO.getUpdateDate()));

            preparedStatement.setLong(4, id);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Menu was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while getting updated Menu"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating Menu");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Menu was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Menu");
        }
    }
}
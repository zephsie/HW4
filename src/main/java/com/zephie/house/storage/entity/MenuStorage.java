package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.SystemMenuDTO;
import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.util.mappers.ResultSetToMenuMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MenuStorage implements IMenuStorage {
    private static volatile MenuStorage instance;


    private static final String SELECT = "SELECT id menu_id, name menu_name, enable menu_enable FROM structure.menu";

    private static final String SELECT_BY_ID = "SELECT id menu_id, name menu_name, enable menu_enable, dt_create, dt_update FROM structure.menu WHERE id = ?";

    private static final String SELECT_MENU_ROWS_BY_MENU_ID = "SELECT menu_row.id menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, price menu_row_price\n" +
            "\tFROM structure.menu_row\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tWHERE menu_id = ?";

    private final DataSource dataSource;

    private MenuStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }

    @Override
    public IMenu create(SystemMenuDTO systemMenuDTO) {
        return null;
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
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

    }

    @Override
    public void read(String name) {

    }

    @Override
    public void readWithoutRows(Long id) {
    }

    public static MenuStorage getInstance() {
        if (instance == null) {
            synchronized (MenuStorage.class) {
                if (instance == null) {
                    instance = new MenuStorage();
                }
            }
        }
        return instance;
    }
}
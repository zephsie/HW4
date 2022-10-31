package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.SystemMenuRowDTO;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.util.DataSourceInitializer;
import com.zephie.house.util.mappers.ResultSetToMenuRowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class MenuRowStorage implements IMenuRowStorage {
    private static volatile MenuRowStorage instance;

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

    private MenuRowStorage() {
        dataSource = DataSourceInitializer.getDataSource();
    }


    @Override
    public IMenuRow create(SystemMenuRowDTO systemMenuRowDTO) {
        return null;
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
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

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

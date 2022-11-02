package com.zephie.house.storage.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.dto.SystemSelectedItemDTO;
import com.zephie.house.storage.api.ISelectedItemStorage;
import com.zephie.house.util.mappers.ResultSetToSelectedItemMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class SelectedItemStorage implements ISelectedItemStorage {
    private final DataSource dataSource;

    private static final String SELECT = "SELECT selected_item.id selected_item_id, menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, menu_id, menu.name menu_name, price menu_row_price, count selected_item_count\n" +
            "\tFROM structure.selected_item\n" +
            "\tJOIN structure.menu_row ON menu_row_id = menu_row.id\n" +
            "\tJOIN structure.menu ON menu_id = menu.id\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id";

    private static final String SELECT_BY_ID = "SELECT selected_item.id selected_item_id, menu_row_id, pizza_info_id, pizza_id, pizza.name pizza_name, size pizza_info_size, menu_id, menu.name menu_name, price menu_row_price, count selected_item_count, selected_item.dt_create, selected_item.dt_update\n" +
            "\tFROM structure.selected_item\n" +
            "\tJOIN structure.menu_row ON menu_row_id = menu_row.id\n" +
            "\tJOIN structure.menu ON menu_id = menu.id\n" +
            "\tJOIN structure.pizza_info ON pizza_info_id = pizza_info.id\n" +
            "\tJOIN structure.pizza ON pizza_id = pizza.id\n" +
            "\tWHERE selected_item.id = ?";

    public SelectedItemStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ISelectedItem create(SystemSelectedItemDTO systemSelectedItemDTO) {
        return null;
    }

    @Override
    public Optional<ISelectedItem> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToSelectedItemMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while reading SelectedItem");
        }
    }

    @Override
    public Collection<ISelectedItem> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Collection<ISelectedItem> selectedItems = new HashSet<>();

                while (resultSet.next()) {
                    selectedItems.add(ResultSetToSelectedItemMapper.partialMap(resultSet));
                }

                return selectedItems;
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while reading SelectedItem");
        }
    }

    @Override
    public ISelectedItem update(Long id, SystemSelectedItemDTO systemSelectedItemDTO, LocalDateTime dateUpdate) {
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

    }
}

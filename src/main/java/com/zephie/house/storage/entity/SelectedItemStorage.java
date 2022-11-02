package com.zephie.house.storage.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.dto.SystemSelectedItemDTO;
import com.zephie.house.storage.api.ISelectedItemStorage;
import com.zephie.house.util.mappers.ResultSetToSelectedItemMapper;

import javax.sql.DataSource;
import java.sql.*;
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

    private static final String INSERT = "INSERT INTO structure.selected_item (menu_row_id, count, dt_create, dt_update) VALUES (?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE structure.selected_item SET menu_row_id = ?, count = ?, dt_update = ? WHERE id = ? AND dt_update = ?";

    private static final String DELETE = "DELETE FROM structure.selected_item WHERE id = ? AND dt_update = ?";

    public SelectedItemStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ISelectedItem create(SystemSelectedItemDTO systemSelectedItemDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, systemSelectedItemDTO.getMenuRowId());
            preparedStatement.setInt(2, systemSelectedItemDTO.getCount());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemSelectedItemDTO.getCreateDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(systemSelectedItemDTO.getUpdateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created SelectedItem"));
                } else {
                    throw new RuntimeException("Something went wrong while creating SelectedItem");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating SelectedItem");
        }
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
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(1, systemSelectedItemDTO.getMenuRowId());
            preparedStatement.setInt(2, systemSelectedItemDTO.getCount());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemSelectedItemDTO.getUpdateDate()));

            preparedStatement.setLong(4, id);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("SelectedItem was not updated");
            }

            return read(id).orElseThrow(() -> new RuntimeException("Something went wrong while reading updated SelectedItem"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while updating SelectedItem");
        }
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateUpdate));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("SelectedItem was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting SelectedItem");
        }
    }
}
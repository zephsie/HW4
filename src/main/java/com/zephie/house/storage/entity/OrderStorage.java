package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.dto.SystemOrderDTO;
import com.zephie.house.core.dto.SystemSelectedItemDTO;
import com.zephie.house.storage.api.IOrderStorage;
import com.zephie.house.util.mappers.entity.ResultSetToOrderMapper;
import com.zephie.house.util.mappers.entity.ResultSetToSelectedItemMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class OrderStorage implements IOrderStorage {
    private final DataSource dataSource;

    private final ResultSetToOrderMapper orderMapper;

    private final ResultSetToSelectedItemMapper selectedItemMapper;

    public OrderStorage(DataSource dataSource, ResultSetToOrderMapper orderMapper, ResultSetToSelectedItemMapper selectedItemMapper) {
        this.dataSource = dataSource;
        this.orderMapper = orderMapper;
        this.selectedItemMapper = selectedItemMapper;
    }

    private static final String INSERT_ORDER = "INSERT INTO structure.order_table (dt_create) VALUES (?)";

    private static final String INSERT_INTO_SELECTED_ITEM = "INSERT INTO structure.selected_item (menu_row_id, order_id, count, dt_create) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ORDER = "SELECT order_id, selected_item.id selected_item_id\n" +
            "\tFROM structure.order_table\n" +
            "\tJOIN structure.selected_item ON order_id = order_table.id";

    private static final String SELECT_ORDER_BY_ID = "SELECT id order_id, dt_create FROM structure.order_table WHERE id = ?";

    private static final String SELECT_SELECTED_ITEM_BY_ORDER_ID = "SELECT selected_item.id selected_item_id FROM structure.selected_item WHERE order_id = ?";

    private static final String DELETE_ORDER = "DELETE FROM structure.order_table WHERE id = ?";

    private static final String DELETE_SELECTED_ITEM_BY_ORDER_ID = "DELETE FROM structure.selected_item WHERE order_id = ?";

    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM structure.order_table WHERE id = ?)";

    @Override
    public IOrder create(SystemOrderDTO systemOrderDTO) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setTimestamp(1, Timestamp.valueOf(systemOrderDTO.getCreateDate()));
                preparedStatement.executeUpdate();

                long orderId;

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getLong(1);
                    } else {
                        throw new RuntimeException("Keys were not generated");
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_SELECTED_ITEM)) {
                    for (SystemSelectedItemDTO systemSelectedItemDTO : systemOrderDTO.getItems()) {
                        statement.setLong(1, systemSelectedItemDTO.getMenuRowId());
                        statement.setLong(2, orderId);
                        statement.setInt(3, systemSelectedItemDTO.getCount());
                        statement.setTimestamp(4, Timestamp.valueOf(systemSelectedItemDTO.getCreateDate()));
                        statement.addBatch();
                    }

                    statement.executeBatch();
                } catch (SQLException e) {
                    throw new RuntimeException("Something went wrong while creating SelectedItem");
                }

                connection.commit();
                return read(orderId).orElseThrow(() -> new RuntimeException("Something went wrong while reading created Order"));
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Something went wrong while creating Order");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not establish connection to database");
        }
    }

    @Override
    public Optional<IOrder> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    IOrder order = orderMapper.fullMap(resultSet);
                    Set<ISelectedItem> selectedItems = new HashSet<>();

                    try (PreparedStatement statementToGetMenuRows = connection.prepareStatement(SELECT_SELECTED_ITEM_BY_ORDER_ID)) {
                        statementToGetMenuRows.setLong(1, id);
                        try (ResultSet resultSetToGetSelectedItems = statementToGetMenuRows.executeQuery()) {
                            while (resultSetToGetSelectedItems.next()) {
                                selectedItems.add(selectedItemMapper.partialMap(resultSetToGetSelectedItems));
                            }
                        }
                    }

                    order.setItems(selectedItems);

                    return Optional.of(order);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Order");
        }
    }

    @Override
    public Collection<IOrder> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Long, IOrder> orders = new HashMap<>();

                while (resultSet.next()) {
                    IOrder order = orderMapper.partialMap(resultSet);
                    ISelectedItem selectedItem = selectedItemMapper.partialMap(resultSet);

                    orders.computeIfAbsent(order.getId(), k -> order).setItems(new HashSet<>(Collections.singletonList(selectedItem)));
                }

                return orders.values();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Order");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SELECTED_ITEM_BY_ORDER_ID)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Something went wrong while deleting SelectedItem");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Something went wrong while deleting Order");
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Can not establish connection to database");
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
            throw new RuntimeException("Something went wrong while checking Order");
        }
    }
}
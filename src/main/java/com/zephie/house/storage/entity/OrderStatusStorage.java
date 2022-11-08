package com.zephie.house.storage.entity;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.dto.SystemOrderStatusDTO;
import com.zephie.house.core.dto.SystemStageStatusDTO;
import com.zephie.house.storage.api.IOrderStatusStorage;
import com.zephie.house.util.mappers.ResultSetToOrderStatusMapper;
import com.zephie.house.util.mappers.ResultSetToStageStatusMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class OrderStatusStorage implements IOrderStatusStorage {
    private final DataSource dataSource;

    public OrderStatusStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SELECT_ORDER_STATUS_BY_ID = "SELECT order_status.id order_status_id, ticket_id, ticket_number, order_status.dt_create\n" +
            "\tFROM structure.order_status\n" +
            "\tJOIN structure.ticket ON ticket.id = ticket_id\n" +
            "\tWHERE order_status.id = ?";

    private static final String SELECT_ORDER_STATUS = "SELECT order_status_id, ticket_id, ticket_number, stage_id, stage.description stage_description, start_time order_status_stage_start_time\n" +
            "\tFROM structure.order_status\n" +
            "\tJOIN structure.ticket ON ticket.id = ticket_id\n" +
            "\tJOIN structure.order_status_stage ON order_status.id = order_status_id\n" +
            "\tJOIN structure.stage ON stage_id = stage.id";

    private static final String SELECT_STAGE_STATUS_BY_ORDER_STATUS_ID = "SELECT stage_id, stage.description stage_description, start_time order_status_stage_start_time\n" +
            "\tFROM structure.order_status_stage\n" +
            "\tJOIN structure.stage ON stage_id = stage.id\n" +
            "\tWHERE order_status_id = ?";

    private static final String IS_ORDER_STATUS_EXISTS = "SELECT EXISTS(SELECT 1 FROM structure.order_status WHERE id = ?)";

    private static final String IS_ORDER_STATUS_EXISTS_BY_TICKET_ID = "SELECT EXISTS(SELECT 1 FROM structure.order_status WHERE ticket_id = ?)";

    private static final String IS_STAGE_STATUS_EXISTS_BY_ORDER_STATUS_ID_AND_STAGE_ID = "SELECT EXISTS(SELECT 1 FROM structure.order_status_stage WHERE order_status_id = ? AND stage_id = ?)";

    private static final String ADD_STAGE_STATUS = "INSERT INTO structure.order_status_stage (order_status_id, stage_id, start_time) VALUES (?, ?, ?)";

    private static final String ADD_ORDER_STATUS = "INSERT INTO structure.order_status (ticket_id, dt_create) VALUES (?, ?)";

    private static final String DELETE_FROM_ORDER_STATUS_STAGE = "DELETE FROM structure.order_status_stage WHERE order_status_id = ?";

    private static final String DELETE_FROM_ORDER_STATUS = "DELETE FROM structure.order_status WHERE id = ?";

    @Override
    public IOrderStatus create(SystemOrderStatusDTO orderStatusDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_STATUS, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, orderStatusDTO.getTicketId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(orderStatusDTO.getCreateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return read(generatedKeys.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created OrderStatus"));
                } else {
                    throw new RuntimeException("Keys were not generated");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating OrderStatus");
        }
    }

    @Override
    public Optional<IOrderStatus> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    IOrderStatus orderStatus = ResultSetToOrderStatusMapper.fullMap(resultSet);
                    Set<IStageStatus> stageStatuses = new HashSet<>();

                    try (PreparedStatement statementToGetStatuses = connection.prepareStatement(SELECT_STAGE_STATUS_BY_ORDER_STATUS_ID)) {
                        statementToGetStatuses.setLong(1, id);
                        try (ResultSet rs = statementToGetStatuses.executeQuery()) {
                            while (rs.next()) {
                                stageStatuses.add(ResultSetToStageStatusMapper.fullMap(rs));
                            }
                        }
                    }

                    orderStatus.setHistory(stageStatuses);

                    return Optional.of(orderStatus);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading OrderStatus");
        }
    }

    @Override
    public Collection<IOrderStatus> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_STATUS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Long, IOrderStatus> orderStatuses = new HashMap<>();

                while (resultSet.next()) {
                    IOrderStatus orderStatus = ResultSetToOrderStatusMapper.partialMap(resultSet);
                    IStageStatus stageStatus = ResultSetToStageStatusMapper.fullMap(resultSet);

                    orderStatuses.computeIfAbsent(orderStatus.getId(), k -> orderStatus).setHistory(new HashSet<>(Collections.singletonList(stageStatus)));
                }

                return orderStatuses.values();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading OrderStatus");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ORDER_STATUS_STAGE)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Something went wrong while deleting StageStatus");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ORDER_STATUS)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Something went wrong while deleting OrderStatus");
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Can not establish connection to database");
        }
    }

    @Override
    public boolean isPresent(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_ORDER_STATUS_EXISTS)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking OrderStatus");
        }
    }

    @Override
    public IOrderStatus addStage(SystemStageStatusDTO systemStageStatusDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(ADD_STAGE_STATUS)) {
            preparedStatement.setLong(1, systemStageStatusDTO.getOrderStatusId());
            preparedStatement.setLong(2, systemStageStatusDTO.getStageId());
            preparedStatement.setTime(3, Time.valueOf(systemStageStatusDTO.getStartTime()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("StageStatus was not added");
            }

            return read(systemStageStatusDTO.getOrderStatusId()).orElseThrow(() -> new RuntimeException("Something went wrong while reading updated OrderStatus"));
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while adding StageStatus");
        }
    }

    @Override
    public boolean isPresentByTicket(Long ticketId) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_ORDER_STATUS_EXISTS_BY_TICKET_ID)) {
            preparedStatement.setLong(1, ticketId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking OrderStatus");
        }
    }

    @Override
    public boolean isStageStatusPresent(Long orderStatusId, Long stageId) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_STAGE_STATUS_EXISTS_BY_ORDER_STATUS_ID_AND_STAGE_ID)) {
            preparedStatement.setLong(1, orderStatusId);
            preparedStatement.setLong(2, stageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking OrderStatus");
        }
    }
}
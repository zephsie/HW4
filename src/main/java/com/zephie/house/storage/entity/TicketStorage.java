package com.zephie.house.storage.entity;

import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.dto.SystemTicketDTO;
import com.zephie.house.storage.api.ITicketStorage;
import com.zephie.house.util.mappers.ResultSetToTicketMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class TicketStorage implements ITicketStorage {
    private final DataSource dataSource;

    private static final String SELECT_TICKET_BY_ID = "SELECT id ticket_id, ticket_number, order_id, dt_create FROM structure.ticket WHERE id = ?";

    private static final String SELECT_TICKET = "SELECT id ticket_id, ticket_number, order_id FROM structure.ticket";
    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM structure.ticket WHERE id = ?)";
    private static final String IS_EXISTS_BY_TICKET_NUMBER = "SELECT EXISTS(SELECT 1 FROM structure.ticket WHERE ticket_number = ?)";
    private static final String IS_EXISTS_BY_ORDER_ID = "SELECT EXISTS(SELECT 1 FROM structure.ticket WHERE order_id = ?)";
    private static final String DELETE = "DELETE FROM structure.ticket WHERE id = ?";
    private static final String INSERT = "INSERT INTO structure.ticket (ticket_number, order_id, dt_create) VALUES (?, ?, ?)";

    public TicketStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ITicket create(SystemTicketDTO systemTicketDTO) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, systemTicketDTO.getTicketNumber());
            preparedStatement.setLong(2, systemTicketDTO.getOrderId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(systemTicketDTO.getCreateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return read(resultSet.getLong(1)).orElseThrow(() -> new RuntimeException("Something went wrong while reading created Ticket"));
                } else {
                    throw new RuntimeException("Keys were not generated");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating Ticket");
        }
    }

    @Override
    public Optional<ITicket> read(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(ResultSetToTicketMapper.fullMap(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Order");
        }
    }

    @Override
    public Collection<ITicket> read() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_TICKET)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                Collection<ITicket> tickets = new HashSet<>();

                while (resultSet.next()) {
                    tickets.add(ResultSetToTicketMapper.partialMap(resultSet));
                }

                return tickets;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while reading Orders");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Order was not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting Order");
        }
    }

    @Override
    public boolean isPresent(String ticket) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_EXISTS_BY_TICKET_NUMBER)) {

            preparedStatement.setString(1, ticket);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking Order");
        }
    }

    @Override
    public boolean isPresentByOrder(Long orderId) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(IS_EXISTS_BY_ORDER_ID)) {

            preparedStatement.setLong(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while checking Order");
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

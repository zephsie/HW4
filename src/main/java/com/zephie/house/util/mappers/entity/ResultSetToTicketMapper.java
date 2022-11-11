package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.builders.TicketBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToTicketMapper {
    private final ResultSetToOrderMapper orderMapper;

    public ResultSetToTicketMapper(ResultSetToOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public ITicket partialMap(ResultSet resultSet) throws SQLException {
        return TicketBuilder.create()
                .setId(resultSet.getLong("ticket_id"))
                .setTicketNumber(resultSet.getString("ticket_number"))
                .setOrder(orderMapper.partialMap(resultSet))
                .build();
    }

    public ITicket fullMap(ResultSet resultSet) throws SQLException {
        return TicketBuilder.create()
                .setId(resultSet.getLong("ticket_id"))
                .setTicketNumber(resultSet.getString("ticket_number"))
                .setOrder(orderMapper.partialMap(resultSet))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .build();
    }
}

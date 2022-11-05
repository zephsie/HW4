package com.zephie.house.util.mappers;

import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.builders.TicketBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToTicketMapper {
    public static ITicket partialMap(ResultSet resultSet) throws SQLException {
        return TicketBuilder.create()
                .setId(resultSet.getLong("ticket_id"))
                .setTicketNumber(resultSet.getString("ticket_number"))
                .setOrder(ResultSetToOrderMapper.partialMap(resultSet))
                .build();
    }

    public static ITicket fullMap(ResultSet resultSet) throws SQLException {
        return TicketBuilder.create()
                .setId(resultSet.getLong("ticket_id"))
                .setTicketNumber(resultSet.getString("ticket_number"))
                .setOrder(ResultSetToOrderMapper.partialMap(resultSet))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .build();
    }
}

package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.builders.OrderStatusBuilder;
import com.zephie.house.core.builders.TicketBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToOrderStatusMapper {
    public IOrderStatus partialMap(ResultSet resultSet) throws SQLException {
        return OrderStatusBuilder.create()
                .setId(resultSet.getLong("order_status_id"))
                .setTicket(TicketBuilder.create()
                        .setId(resultSet.getLong("ticket_id"))
                        .setTicketNumber(resultSet.getString("ticket_number"))
                        .build())
                .build();
    }

    public IOrderStatus fullMap(ResultSet resultSet) throws SQLException {
        return OrderStatusBuilder.create()
                .setId(resultSet.getLong("order_status_id"))
                .setTicket(TicketBuilder.create()
                        .setId(resultSet.getLong("ticket_id"))
                        .setTicketNumber(resultSet.getString("ticket_number"))
                        .build())
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .build();
    }
}

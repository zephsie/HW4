package com.zephie.house.storage.api;

import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.dto.SystemTicketDTO;

public interface ITicketStorage extends IImmutableEssenceStorage<ITicket, SystemTicketDTO> {
    boolean isPresent(String ticket);

    boolean isPresentByOrder(Long orderId);
}

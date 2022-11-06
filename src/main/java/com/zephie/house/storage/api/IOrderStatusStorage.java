package com.zephie.house.storage.api;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.dto.SystemOrderStatusDTO;
import com.zephie.house.core.dto.SystemStageStatusDTO;

public interface IOrderStatusStorage extends IImmutableEssenceStorage<IOrderStatus, SystemOrderStatusDTO> {
    IOrderStatus addStage(SystemStageStatusDTO systemStageStatusDTO);

    boolean isPresentByTicket(Long ticketId);

    boolean isStageStatusPresent(Long orderStatusId, Long stageId);
}

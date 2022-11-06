package com.zephie.house.services.api;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.dto.OrderStatusDTO;
import com.zephie.house.core.dto.StageStatusDTO;

public interface IOrderStatusService extends IImmutableEssenceService<IOrderStatus, OrderStatusDTO> {
    IOrderStatus addStage(StageStatusDTO StageStatusDTO);
}

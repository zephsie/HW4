package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.OrderStatusDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicOrderStatusValidator;

public class BasicOrderStatusValidatorSingleton {
    private static volatile BasicOrderStatusValidatorSingleton instance;

    private final IValidator<OrderStatusDTO> validator;

    private BasicOrderStatusValidatorSingleton() {
        validator = new BasicOrderStatusValidator();
    }

    public static IValidator<OrderStatusDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicOrderStatusValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicOrderStatusValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}

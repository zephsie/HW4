package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.OrderDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicOrderValidator;

public class BasicOrderValidatorSingleton {
    private static volatile BasicOrderValidatorSingleton instance;

    private final IValidator<OrderDTO> validator;

    private BasicOrderValidatorSingleton() {
        validator = new BasicOrderValidator();
    }

    public static IValidator<OrderDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicOrderValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicOrderValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}

package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicPizzaInfoValidator;

public class BasicPizzaInfoValidatorSingleton {
    private static volatile BasicPizzaInfoValidatorSingleton instance;

    private final IValidator<PizzaInfoDTO> basicPizzaInfoValidator;

    private BasicPizzaInfoValidatorSingleton() {
        basicPizzaInfoValidator = new BasicPizzaInfoValidator();
    }

    public static IValidator<PizzaInfoDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicPizzaInfoValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicPizzaInfoValidatorSingleton();
                }
            }
        }

        return instance.basicPizzaInfoValidator;
    }
}
package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicPizzaValidator;

public class BasicPizzaValidatorSingleton {
    private static volatile BasicPizzaValidatorSingleton instance;

    private final IValidator<PizzaDTO> basicPizzaValidator;

    private BasicPizzaValidatorSingleton() {
        basicPizzaValidator = new BasicPizzaValidator();
    }

    public static IValidator<PizzaDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicPizzaValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicPizzaValidatorSingleton();
                }
            }
        }

        return instance.basicPizzaValidator;
    }
}
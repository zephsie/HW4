package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.MenuDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicMenuValidator;

public class BasicMenuValidatorSingleton {
    private static volatile BasicMenuValidatorSingleton instance;
    private final IValidator<MenuDTO> basicMenuValidator;

    private BasicMenuValidatorSingleton() {
        basicMenuValidator = new BasicMenuValidator();
    }

    public static IValidator<MenuDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicMenuValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicMenuValidatorSingleton();
                }
            }
        }

        return instance.basicMenuValidator;
    }
}

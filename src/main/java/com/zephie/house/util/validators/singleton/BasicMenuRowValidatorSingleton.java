package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicMenuRowValidator;

public class BasicMenuRowValidatorSingleton {
    private static volatile BasicMenuRowValidatorSingleton instance;

    private final IValidator<MenuRowDTO> validator;

    private BasicMenuRowValidatorSingleton() {
        validator = new BasicMenuRowValidator();
    }

    public static IValidator<MenuRowDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicMenuRowValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicMenuRowValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}

package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.StageDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicStageValidator;

public class BasicStageValidatorSingleton {
    private static volatile BasicStageValidatorSingleton instance;

    private final IValidator<StageDTO> validator;

    private BasicStageValidatorSingleton() {
        validator = new BasicStageValidator();
    }

    public static IValidator<StageDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicStageValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicStageValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}

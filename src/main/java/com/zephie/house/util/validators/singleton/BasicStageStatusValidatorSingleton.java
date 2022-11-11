package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.StageStatusDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicStageStatusValidator;

public class BasicStageStatusValidatorSingleton {
    private static volatile BasicStageStatusValidatorSingleton instance;

    private final IValidator<StageStatusDTO> validator;

    private BasicStageStatusValidatorSingleton() {
        validator = new BasicStageStatusValidator();
    }

    public static IValidator<StageStatusDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicStageStatusValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicStageStatusValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}
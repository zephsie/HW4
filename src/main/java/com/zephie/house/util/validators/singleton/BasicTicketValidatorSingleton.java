package com.zephie.house.util.validators.singleton;

import com.zephie.house.core.dto.TicketDTO;
import com.zephie.house.util.validators.api.IValidator;
import com.zephie.house.util.validators.entity.BasicTicketValidator;

public class BasicTicketValidatorSingleton {
    private static volatile BasicTicketValidatorSingleton instance;

    private final IValidator<TicketDTO> validator;

    private BasicTicketValidatorSingleton() {
        validator = new BasicTicketValidator();
    }

    public static IValidator<TicketDTO> getInstance() {
        if (instance == null) {
            synchronized (BasicTicketValidatorSingleton.class) {
                if (instance == null) {
                    instance = new BasicTicketValidatorSingleton();
                }
            }
        }

        return instance.validator;
    }
}

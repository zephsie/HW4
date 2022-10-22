package com.zephie.house.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class DataSourceInitializer {
    private static volatile DataSourceInitializer instance;
    private final ComboPooledDataSource dataSource;

    private DataSourceInitializer() {
        dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/pizza_house");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setAcquireIncrement(5);
    }

    public static DataSource getDataSource() {
        if (instance == null) {
            synchronized (DataSourceInitializer.class) {
                if (instance == null) {
                    instance = new DataSourceInitializer();
                }
            }
        }

        return instance.dataSource;
    }
}

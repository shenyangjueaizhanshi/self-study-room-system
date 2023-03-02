package com.example.selfstudyroomsystem.config.mybatis.properties;

import lombok.Data;

@Data
public class BaseJdbcProperties {
    private String driverClass;

    private String jdbcUrl;

    private String username;

    private String password;

    private Long minEvictableIdleTimeMillis;

    private Integer maxActive;
}

package com.example.selfstudyroomsystem.config.mybatis.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = true)
@Component("jdbcProperties")
public class JdbcProperties extends BaseJdbcProperties{

    @Value("${local.driverClass}")
    private String driverClass;

    @Value("${local.jdbcUrl}")
    private String jdbcUrl;

    @Value("${local.username}")
    private String username;

    @Value("${local.password}")
    private String password;

    @Value("${local.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${local.maxActive}")
    private Integer maxActive;
}

package com.example.selfstudyroomsystem.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.selfstudyroomsystem.config.mybatis.properties.BaseDBProperties;
import com.example.selfstudyroomsystem.config.mybatis.properties.BaseJdbcProperties;
import com.example.selfstudyroomsystem.config.mybatis.properties.JdbcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Slf4j
public class BaseDBConfiguration {
    @Autowired
    private BaseDBProperties dbProperties;

    @Autowired
    private JdbcProperties jdbcProperties;


    /**
     * 生成数据源配置
     * @return
     */
    public DataSource generateDataSource() {
        log.debug(">>>>> BaseDBConfiguration:fundDB={}", jdbcProperties.getJdbcUrl());
        return createCommonDataSource(jdbcProperties);
    }
    /**
     * 生成公共数据源属性配置
     *
     */
    private DruidDataSource createCommonDataSource(BaseJdbcProperties jdbcProperties) {
        DruidDataSource datasource = new DruidDataSource();
        //连接数据库的url，不同数据库不一样。
        datasource.setUrl(jdbcProperties.getJdbcUrl());
        //连接数据库的用户名
        datasource.setUsername(jdbcProperties.getUsername());
        //连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter
        datasource.setPassword(jdbcProperties.getPassword());
        //默认可根据URL自动识别。这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName。
        datasource.setDriverClassName(jdbcProperties.getDriverClass());
        //连接保持空闲而不被驱逐的最小时间，单位毫秒。
        datasource.setMinEvictableIdleTimeMillis(jdbcProperties.getMinEvictableIdleTimeMillis());
        //默认值8。最大连接池数量。
        datasource.setMaxActive(jdbcProperties.getMaxActive());

        //默认值0。初始化时建立物理连接的个数，初始化发生在显示调用init方法，或者第一次getConnection时。
        datasource.setInitialSize(dbProperties.getInitialSize());
        //最小连接池数量。
        datasource.setMinIdle(dbProperties.getMinIdle());
        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true来使用非公平锁。
        datasource.setMaxWait(dbProperties.getMaxWait() == 0 ? dbProperties.getMaxWait() : 6000);
        // 1.0.14版本，默认值为1分钟，单位毫秒。有两个含义：一个是Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接；另一个是testWhileIdle的判断依据，详细看testWhileIdle属性的说明。
        datasource.setTimeBetweenEvictionRunsMillis(dbProperties.getTimeBetweenEvictionRunsMillis());
        //用来检测连接是否有效的sql，要求是一个查询语句，常用select ‘X’。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        datasource.setValidationQuery(dbProperties.getValidationQuery());
        //默认值为false。建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        datasource.setTestWhileIdle(dbProperties.isTestWhileIdle());
        //默认值为true。申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        datasource.setTestOnBorrow(dbProperties.isTestOnBorrow());
        //默认值为false。归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        datasource.setTestOnReturn(dbProperties.isTestOnReturn());
        //默认值为false。是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        datasource.setPoolPreparedStatements(dbProperties.isPoolPreparedStatements());
        //默认值-1。要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100。
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dbProperties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(dbProperties.getFilters());
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        return datasource;
    }
}

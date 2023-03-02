package com.example.selfstudyroomsystem.config.mybatis;

import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
@MapperScan(basePackages = {"com.example.selfstudyroomsystem.dao"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class DBConfiguration extends BaseDBConfiguration {

    @Bean(name = "dataSource")
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){
        return generateDataSource();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(
//                "classpath:mapper/jydb/*.xml"));
        SqlSessionFactory sqlSessionFactory;
        bean.setTypeAliasesPackage("com.example.selfstudyroomsystem.bean");

        //分页插件
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("supportMethodsArguments", "false");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        //添加插件
        bean.setPlugins(new Interceptor[] {interceptor});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            List<Resource> mapperLocations = new ArrayList<>();
            mapperLocations.addAll(Arrays.asList(resolver.getResources("classpath:mapper/dao/**/*.xml")));
            bean.setMapperLocations(mapperLocations.toArray(new Resource[]{}));
            sqlSessionFactory = bean.getObject();
        } catch (Exception e) {
            log.error("in sqlSessionFactoryBean ", e);
            throw new RuntimeException(e);
        }

        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

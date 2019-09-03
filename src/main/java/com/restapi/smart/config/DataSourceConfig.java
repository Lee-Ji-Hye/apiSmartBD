package com.restapi.smart.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//Mybatis 자바콩기반 설정 파일
@Configuration
public class DataSourceConfig {

    //application.properties의 datasource 설정을 가져오는 부분
    @Bean(name = "DBDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "DBDataSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("DBDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //mapper의 위치를 지정하는 부분
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(
                        "classpath:mappers/**")
        );
        return bean.getObject();
    }

    @Bean(name = "DBDataTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("DBDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "DBDataSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("DBDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
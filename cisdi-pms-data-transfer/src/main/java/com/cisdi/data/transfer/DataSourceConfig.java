package com.cisdi.data.transfer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "cpmsDataSource")
    @Qualifier("cpmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cpms")
    public DataSource cpmsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cpmsJdbcTemplate")
    public JdbcTemplate cpmsJdbcTemplate(@Qualifier("cpmsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "testDataSource")
    @Qualifier("testDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSource testDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "testJdbcTemplate")
    public JdbcTemplate testJdbcTemplate(@Qualifier("testDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "seedDataSource")
    @Qualifier("seedDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.seed")
    public DataSource seedDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "seedJdbcTemplate")
    public JdbcTemplate seedJdbcTemplate(@Qualifier("seedDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "prodDataSource")
    @Qualifier("prodDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.prod")
    public DataSource prodDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "prodJdbcTemplate")
    public JdbcTemplate prodJdbcTemplate(@Qualifier("prodDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
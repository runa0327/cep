package com.cisdi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Dynamic DataSource Configuration
 * Provides dynamic datasource routing capability for multi-tenant or multi-database scenarios
 * 
 * Created: 2026-01-06 05:58:47 UTC
 * Author: runa0327
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceConfig.class)
public class DynamicDataSourceConfig {

    /**
     * Thread-local variable for storing the current datasource key
     */
    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<>();

    /**
     * Set the datasource key for the current thread
     * 
     * @param key the datasource key
     */
    public static void setDataSourceKey(String key) {
        DATA_SOURCE_KEY.set(key);
    }

    /**
     * Get the datasource key for the current thread
     * 
     * @return the datasource key
     */
    public static String getDataSourceKey() {
        return DATA_SOURCE_KEY.get();
    }

    /**
     * Clear the datasource key from the current thread
     */
    public static void clearDataSourceKey() {
        DATA_SOURCE_KEY.remove();
    }

    /**
     * Dynamic routing datasource that determines which physical datasource to use at runtime
     */
    @Component
    public static class DynamicRoutingDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            String key = DynamicDataSourceConfig.getDataSourceKey();
            return key != null ? key : "master";
        }
    }

    /**
     * Configure the dynamic datasource with routing capability
     * 
     * @param masterDataSource the master datasource
     * @param slaveDataSource the slave datasource (optional)
     * @return configured datasource with routing
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(
            DataSource masterDataSource) {
        
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);
        
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.afterPropertiesSet();
        
        return routingDataSource;
    }

    /**
     * Configuration properties for dynamic datasource settings
     */
    @ConfigurationProperties(prefix = "spring.datasource.dynamic")
    public static class DynamicDataSourceProperties {
        
        private boolean enabled = false;
        private String defaultDataSource = "master";
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public String getDefaultDataSource() {
            return defaultDataSource;
        }
        
        public void setDefaultDataSource(String defaultDataSource) {
            this.defaultDataSource = defaultDataSource;
        }
    }
}

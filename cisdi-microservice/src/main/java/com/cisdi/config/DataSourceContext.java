package com.cisdi.config;

/**
 * 数据源上下文
 * 使用 ThreadLocal 存储当前线程的数据库标识
 */
public class DataSourceContext {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据库标识
     */
    public static void setDbKey(String dbKey) {
        contextHolder.set(dbKey);
    }

    /**
     * 获取数据库标识，默认为 cisdi_db1
     */
    public static String getDbKey() {
        String dbKey = contextHolder.get();
        return dbKey == null ? "cisdi_db1" : dbKey;
    }

    /**
     * 清除数据源标识
     */
    public static void clear() {
        contextHolder.remove();
    }
}
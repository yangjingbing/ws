package com.ws.until;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yjb
 * @date 2022/6/23 16:11
 */
public class JDBCUtil {
    public static final String username = "root";
    public static final String password = "123456";
    public static final String jdbcUrl = "jdbc:mysql://localhost:3306/zf?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false&amp;zeroDateTimeBehavior=round<";
    public static final String driverName = "com.mysql.jdbc.Driver";

    public static JdbcTemplate getJdbcTemplate() {

        // com.alibaba.druid.pool.DruidDataSource
        DataSource dataSource = new DataSource();

        // 设置数据源属性参数
        dataSource.setPassword(password);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverName);

        // 获取spring的JdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 设置数据源
        jdbcTemplate.setDataSource(dataSource);

        return jdbcTemplate;
    }

}

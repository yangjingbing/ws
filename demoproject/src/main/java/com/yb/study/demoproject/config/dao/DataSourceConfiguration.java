package com.yb.study.demoproject.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @author yjb
 * @date 2022/1/22 15:54
 */
@Configuration
public class DataSourceConfiguration {
    @Value("${jdbc.Driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    public ComboPooledDataSource createDataSource() throws PropertyVetoException {

    }

}

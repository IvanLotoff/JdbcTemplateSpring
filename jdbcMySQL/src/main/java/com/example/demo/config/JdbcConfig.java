package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.sql.Driver;

/*
@Configuration
@ComponentScan("com.example.demo")
public class JdbcConfig {
    @Bean
    public DataSource mySqlDataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try {
            dataSource.setDriverClass(new com.mysql.cj.jdbc.Drive());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataSource.setUrl("jdbc:mysql:localhost:3307/department");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
*/

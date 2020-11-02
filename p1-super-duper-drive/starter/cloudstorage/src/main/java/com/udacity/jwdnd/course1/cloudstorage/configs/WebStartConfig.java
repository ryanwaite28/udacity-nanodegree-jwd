package com.udacity.jwdnd.course1.cloudstorage.configs;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class WebStartConfig {
//  @Bean
  public DataSource dataSource() {
    // https://stackoverflow.com/questions/24232892/spring-boot-and-sqlite
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName("org.sqlite.JDBC");
    dataSourceBuilder.url("jdbc:sqlite:database.db");
    return dataSourceBuilder.build();
  }
}

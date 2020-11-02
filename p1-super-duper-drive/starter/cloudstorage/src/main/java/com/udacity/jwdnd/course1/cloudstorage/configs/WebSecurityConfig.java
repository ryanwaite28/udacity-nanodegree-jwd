package com.udacity.jwdnd.course1.cloudstorage.configs;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  private String[] publicLinks = {
    "/",
    "/signup",
    "/users",
    "/users/**",
    "/css/**",
    "/js/**",
    "/img/**",
  };

  @Autowired
  private AuthService authService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(this.authService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(this.publicLinks)
      .permitAll()
      .anyRequest()
      .authenticated();

    http.formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/login")
      .permitAll();

    http.formLogin()
      .failureUrl("/login?invalidCred=true")
      .defaultSuccessUrl("/home", true);
  }
}

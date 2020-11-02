package com.udacity.jwdnd.course1.cloudstorage.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLoginDto {
  @NotNull
  @NotEmpty
  private String username;
  
  @NotNull
  @NotEmpty
  private String password;
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}

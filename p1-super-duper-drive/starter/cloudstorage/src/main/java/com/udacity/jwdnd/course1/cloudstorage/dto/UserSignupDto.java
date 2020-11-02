/*
  DTO = Data Transfer Object
  https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
*/
package com.udacity.jwdnd.course1.cloudstorage.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserSignupDto {
  @NotNull
  @NotEmpty
  private String firstName;
  
  @NotNull
  @NotEmpty
  private String lastName;
  
  @NotNull
  @NotEmpty
  private String username;
  
  @NotNull
  @NotEmpty
  private String password;
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
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

package com.udacity.jwdnd.course1.cloudstorage.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CredentialDto {
  @NotNull
  @NotEmpty
  private String url;
  
  @NotNull
  @NotEmpty
  private String username;
  
  @NotNull
  @NotEmpty
  private String password;
  
  @NotNull
  @NotEmpty
  private String salt;
  
  private Integer userId;
  private Integer credentialId;
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
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
  
  public String getSalt() {
    return salt;
  }
  
  public void setSalt(String salt) {
    this.salt = salt;
  }
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
  
  public Integer getCredentialId() {
    return credentialId;
  }
  
  public void setCredentialId(Integer credentialId) {
    this.credentialId = credentialId;
  }
}

package com.udacity.jwdnd.course1.cloudstorage.models;

import java.io.Serializable;

public class Credential implements Serializable {
  private Integer credentialId;
  private String url;
  private String username;
  private String salt;
  private String password;
  private Integer userId;
  
  public Credential(
    Integer credentialId,
    Integer userId,
    String url,
    String username,
    String password,
    String salt
  ) {
    this.credentialId = credentialId;
    this.url = url;
    this.username = username;
    this.salt = salt;
    this.password = password;
    this.userId = userId;
  }
  
  public Integer getCredentialId() {
    return credentialId;
  }
  
  public void setCredentialId(Integer credentialId) {
    this.credentialId = credentialId;
  }
  
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
  
  public String getSalt() {
    return salt;
  }
  
  public void setSalt(String salt) {
    this.salt = salt;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}

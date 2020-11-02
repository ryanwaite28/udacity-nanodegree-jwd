package com.udacity.jwdnd.course1.cloudstorage.enums;

public enum Constants {
  ANONYMOUS_KEY("-123"),
  ANONYMOUS_PRINCIPAL("anonymousUser"),
  ANONYMOUS_AUTHORITY("anonymousAuthority"),
  
  AUTHORIZED_AUTHORITY("authorizedAuthority");
  
  private String constant;
  
  Constants(String constant) {
    this.constant = constant;
  }
  
  public String getConstant() {
    return this.constant;
  }
  public void setConstant(String constant) {
    this.constant = constant;
  }
}

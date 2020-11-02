package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  public boolean checkPassword(String password, String confirm) {
    boolean is7orGreater = password.length() > 6;
    boolean doesMatch = confirm.equals(password);
    boolean isValid = is7orGreater && doesMatch;
    return isValid;
  }
  
  public String getHash(String str) {
    String hash = bCryptPasswordEncoder.encode(str);
    return hash;
  }
  
}

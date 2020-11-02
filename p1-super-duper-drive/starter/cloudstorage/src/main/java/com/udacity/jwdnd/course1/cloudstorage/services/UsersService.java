package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserSignupDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersService {
  private Logger logger = LoggerFactory.getLogger(UsersService.class);
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private HashService hashService;
  
  public User getUserByUsername(String username) {
    try {
      User user = this.userMapper.getUser(username);
      return user;
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      return null;
    }
  }
  
  public User signUp(UserSignupDto userSignupDto) {
    try {
      String salt = this.hashService.generateSalt();
      String pswrd = userSignupDto.getPassword();
      String hashedPassword = this.hashService.getHashedValue(pswrd, salt);
      User newUser = new User(
        null,
        userSignupDto.getUsername(),
        salt,
        hashedPassword,
        userSignupDto.getFirstName(),
        userSignupDto.getLastName()
      );
      int newUserId = this.userMapper.insert(newUser);
      newUser.setUserId(newUserId);
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        userSignupDto.getUsername(),
        pswrd,
        new ArrayList<>()
      );
      SecurityContextHolder.getContext().setAuthentication(token);
      return newUser;
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      return null;
    }
  }
}

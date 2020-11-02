package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.enums.Constants;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthService implements AuthenticationProvider {
  private Logger logger = LoggerFactory.getLogger(AuthService.class);
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private HashService hashService;
    
  public Boolean isUserLoggedIn() {
    try {
      Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
      boolean theUserIsLoggedIn = (
        authObj != null &&
        authObj.isAuthenticated() &&
        !(authObj instanceof AnonymousAuthenticationToken)
      );
      return theUserIsLoggedIn;
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      return null;
    }
  }
  
  public boolean logOut() {
    try {
      Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Constants.ANONYMOUS_AUTHORITY.toString());
      roles.add(authority);
      AnonymousAuthenticationToken anon = new AnonymousAuthenticationToken(
        Constants.ANONYMOUS_KEY.toString(),
        Constants.ANONYMOUS_PRINCIPAL.toString(),
        roles
      );
      SecurityContextHolder.getContext().setAuthentication(anon);
      return true;
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      return false;
    }
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName().toString();
    User user = userMapper.getUser(username);
    if (user == null) {
      return null;
    }
  
    String password = authentication.getCredentials().toString();
    String encodedSalt = user.getSalt();
    String hashedPassword = hashService.getHashedValue(password, encodedSalt);
    boolean invalidPassword = !user.getPassword().equals(hashedPassword);
    if (invalidPassword) {
      return null;
    }
  
    Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Constants.AUTHORIZED_AUTHORITY.toString());
    roles.add(authority);
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
      username,
      password,
      roles
    );
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

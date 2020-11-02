package com.example.demo.services;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.*;
import com.splunk.logging.*;
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired
  private UserRepository userRepository;
  
  private static final Logger LOGGER = LoggerFactory.getLogger("main_logger");
//  private static final org.apache.log4j.Logger LOGGER2 = org.apache.log4j.LogManager.getLogger(UserDetailsServiceImpl.class);
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LOGGER.info("{ \"event\": " + "\"[LOGIN-ATTEMPT] username: " + username +  "\", \"source\": \"cartapp\" }");
    User user = userRepository.findByUsername(username);
    if (user == null) {
      LOGGER.info("{ \"event\": " + "\"[LOGIN-FAILED] username: " + username +  "\", \"source\": \"cartapp\" }");
      throw new UsernameNotFoundException(username);
    }
    LOGGER.info("{ \"event\": " + "\"[LOGIN-SUCCESS] username: " + username +  "\", \"source\": \"cartapp\" }");
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      emptyList()
    );
  }
  
}

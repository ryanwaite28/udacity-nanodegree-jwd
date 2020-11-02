package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.configs.SecurityConstants;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
  
  @LocalServerPort
  private int port;
  
  @Autowired
  private UserController userController;
  
  @Autowired
  private TestRestTemplate restTemplate;
  
  private String jwt;
  
  public HttpEntity prepareRequest(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_STRING, jwt);
    HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers);
    return httpEntity;
  }
  
  @Before
  public void CreateUserAndLogIn() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername("username");
    createUserRequest.setPassword("password123");
    createUserRequest.setConfirmPassword("password123");
    ResponseEntity<User> response_createUser = restTemplate.postForEntity(
      "http://localhost:" + port + "/api/user/create",
      createUserRequest,
      User.class
    );
    HashMap<String, String> loginBody = new HashMap<>();
    loginBody.put("username", "username");
    loginBody.put("password", "password123");
    ResponseEntity<User> response_login = restTemplate.postForEntity(
      "http://localhost:" + port + "/login",
      loginBody,
      User.class
    );
    
    String auth = response_login.getHeaders().getFirst(SecurityConstants.HEADER_STRING);
    this.jwt = auth;
  }
  
  @Test
  public void findById() {
    ResponseEntity<User> response = restTemplate.exchange(
      "http://localhost:" + port + "/api/user/id/1",
      HttpMethod.GET,
      prepareRequest(null),
      User.class
    );
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    User user = response.getBody();
    Assertions.assertEquals(1, user.getId());
    Assertions.assertEquals("username", user.getUsername());
  }
  
  @Test
  public void findByUserName() {
    ResponseEntity<User> response = restTemplate.exchange(
      "http://localhost:" + port + "/api/user/username",
      HttpMethod.GET,
      prepareRequest(null),
      User.class
    );
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    User user = response.getBody();
    Assertions.assertEquals(1, user.getId());
    Assertions.assertEquals("username", user.getUsername());
  }
  
  @Test
  public void badFindByUserName() {
    ResponseEntity<User> response = restTemplate.exchange(
      "http://localhost:" + port + "/api/user/user",
      HttpMethod.GET,
      prepareRequest(null),
      User.class
    );
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void badCreateUser() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername("username");
    createUserRequest.setPassword("pa");
    createUserRequest.setConfirmPassword("");
    ResponseEntity<User> response_createUser = restTemplate.postForEntity(
      "http://localhost:" + port + "/api/user/create",
      createUserRequest,
      User.class
    );
    Assertions.assertEquals(response_createUser.getStatusCode(), HttpStatus.BAD_REQUEST);
  }
  
}

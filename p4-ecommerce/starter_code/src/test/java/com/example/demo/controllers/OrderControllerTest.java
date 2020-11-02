package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.configs.SecurityConstants;
import com.example.demo.model.persistence.Item;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
  
  @LocalServerPort
  private int port;
  
  @Autowired
  private OrderController orderController;
  
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
  public void submit() {
    ResponseEntity<UserOrder> response_submit = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/submit/username",
      HttpMethod.POST,
      prepareRequest(null),
      UserOrder.class
    );
    Assertions.assertEquals(response_submit.getStatusCode(), HttpStatus.OK);
    UserOrder userOrder = response_submit.getBody();
    Assertions.assertEquals(null, userOrder.getTotal());
    Assertions.assertEquals(0, userOrder.getItems().size());
  }
  
  @Test
  public void badSubmit() {
    ResponseEntity<UserOrder> response_submit = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/submit/user",
      HttpMethod.POST,
      prepareRequest(null),
      UserOrder.class
    );
    Assertions.assertEquals(response_submit.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void getOrdersForUser() {
    ResponseEntity<List> response_history = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/history/username",
      HttpMethod.GET,
      prepareRequest(null),
      List.class
    );
    Assertions.assertEquals(response_history.getStatusCode(), HttpStatus.OK);
    List<UserOrder> userOrders = response_history.getBody();
    Assertions.assertEquals(0, userOrders.size());
  }
  
  @Test
  public void badGetOrdersForUser() {
    ResponseEntity<List> response_history = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/history/user",
      HttpMethod.GET,
      prepareRequest(null),
      List.class
    );
    Assertions.assertEquals(response_history.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  
}

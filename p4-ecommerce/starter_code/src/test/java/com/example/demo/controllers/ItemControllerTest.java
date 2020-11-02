package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.configs.SecurityConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
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
public class ItemControllerTest {
  
  @LocalServerPort
  private int port;
  
  @Autowired
  private ItemController itemController;
  
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
  public void getItems() {
    ResponseEntity<List> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/item",
      HttpMethod.GET,
      prepareRequest(null),
      List.class
    );
    Assertions.assertEquals(response_addToCart.getStatusCode(), HttpStatus.OK);
    List<Item> items = response_addToCart.getBody();
    Assertions.assertEquals(items.size(), 5);
  }
  
  @Test
  public void getItemById() {
    ResponseEntity<Item> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/item/1",
      HttpMethod.GET,
      prepareRequest(null),
      Item.class
    );
    Assertions.assertEquals(response_addToCart.getStatusCode(), HttpStatus.OK);
    Item item = response_addToCart.getBody();
    Assertions.assertEquals(item.getId(), 1);
  }
  
  @Test
  public void getItemsByName() {
    // find by invalid name
    ResponseEntity<List> response_getItemsByName = restTemplate.exchange(
      "http://localhost:" + port + "/api/item/name/Blank Widget",
      HttpMethod.GET,
      prepareRequest(null),
      List.class
    );
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response_getItemsByName.getStatusCode());
    
    // find by valid name
    ResponseEntity<List> response_getItemsByNameAgain = restTemplate.exchange(
      "http://localhost:" + port + "/api/item/name/Round Widget",
      HttpMethod.GET,
      prepareRequest(null),
      List.class
    );
    Assertions.assertEquals(response_getItemsByNameAgain.getStatusCode(), HttpStatus.OK);
    List<Item> items = response_getItemsByNameAgain.getBody();
    Assertions.assertEquals(items.size(), 1);
  }
  
}

package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.configs.SecurityConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
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
import org.junit.Before;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest {
  
  @LocalServerPort
  private int port;
  
  @Autowired
  private CartController cartController;
  
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
    jwt = auth;
  }
  
  @Test
  public void addingAndRemovingFromCart() {
    // add item
    ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
    modifyCartRequest.setUsername("username");
    modifyCartRequest.setItemId(1);
    modifyCartRequest.setQuantity(1);
    ResponseEntity<Cart> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/addToCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(response_addToCart.getStatusCode(), HttpStatus.OK);
    Cart cart = response_addToCart.getBody();
    Assertions.assertEquals(cart.getItems().size(), 1);
    Assertions.assertEquals(cart.getTotal().doubleValue(), 2.99);
    Assertions.assertEquals(cart.getItems().get(0).getId(), 1);
    
    // remove item
    ResponseEntity<Cart> response_removeFromcart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/removeFromCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(HttpStatus.OK, response_removeFromcart.getStatusCode());
    cart = response_removeFromcart.getBody();
    Assertions.assertEquals(0, cart.getItems().size());
    Assertions.assertEquals(0, cart.getTotal().intValue());
  }
  
  @Test
  public void badRequest_Username() {
    // bad add username
    ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
    modifyCartRequest.setUsername("user");
    modifyCartRequest.setItemId(1);
    modifyCartRequest.setQuantity(1);
    ResponseEntity<Cart> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/addToCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response_addToCart.getStatusCode());
  
    // bad remove username
    ResponseEntity<Cart> response_removeFromcart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/removeFromCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response_removeFromcart.getStatusCode());
  }
  
  @Test
  public void badRequest_itemId() {
    // add add item
    ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
    modifyCartRequest.setUsername("username");
    modifyCartRequest.setItemId(6);
    modifyCartRequest.setQuantity(1);
    ResponseEntity<Cart> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/addToCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response_addToCart.getStatusCode());
  
    // bad remove item
    ResponseEntity<Cart> response_removeFromcart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/removeFromCart",
      HttpMethod.POST,
      prepareRequest(modifyCartRequest),
      Cart.class
    );
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response_removeFromcart.getStatusCode());
  }
  
}

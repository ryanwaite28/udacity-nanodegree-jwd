package com.example.demo;

import com.example.demo.configs.SecurityConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SareetaApplicationTests {
  
  @LocalServerPort
  private int port;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
	}
	
	@Test
  public void testUnauthorizedAccess() {
	  // cart controller
    ResponseEntity<Object> response_addToCart = restTemplate.postForEntity(
      "http://localhost:" + port + "/api/cart/addToCart",
      new HashMap<>(),
      Object.class
    );
    ResponseEntity<Object> response_removeFromCart = restTemplate.postForEntity(
      "http://localhost:" + port + "/api/cart/removeFromCart",
      new HashMap<>(),
      Object.class
    );
    Assertions.assertEquals(response_addToCart.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertEquals(response_removeFromCart.getStatusCode(), HttpStatus.FORBIDDEN);
    
    // item controller
    ResponseEntity<Object> response_getItems = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/item",
      Object.class
    );
    ResponseEntity<Object> response_getItemById = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/item/1",
      Object.class
    );
    ResponseEntity<Object> response_getItemsByName = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/item/name/aName",
      Object.class
    );
    Assertions.assertEquals(response_getItems.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertEquals(response_getItemById.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertEquals(response_getItemsByName.getStatusCode(), HttpStatus.FORBIDDEN);
    
    // order controller
    ResponseEntity<Object> response_submit = restTemplate.postForEntity(
      "http://localhost:" + port + "/api/order/submmit/aName",
      new HashMap<>(),
      Object.class
    );
    ResponseEntity<Object> response_getOrdersForUser = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/order/history/aName",
      Object.class
    );
    Assertions.assertEquals(response_submit.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertEquals(response_getOrdersForUser.getStatusCode(), HttpStatus.FORBIDDEN);
    
    // user controller
    ResponseEntity<Object> response_findById = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/user",
      Object.class
    );
    ResponseEntity<Object> response_findByUserName = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/user/1",
      Object.class
    );
    Assertions.assertEquals(response_findById.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertEquals(response_findByUserName.getStatusCode(), HttpStatus.FORBIDDEN);
  }
  
  @Test
  public void testAuthorizedAccess() {
    // first, login and get token
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
    
    String jwt = response_login.getHeaders().getFirst(SecurityConstants.HEADER_STRING);
    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_STRING, jwt);
    HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
  
    // cart controller
    ResponseEntity<Object> response_addToCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/addToCart",
      HttpMethod.POST,
      httpEntity,
      Object.class
    );
    ResponseEntity<Object> response_removeFromCart = restTemplate.exchange(
      "http://localhost:" + port + "/api/cart/removeFromCart",
      HttpMethod.POST,
      httpEntity,
      Object.class
    );
    Assertions.assertNotEquals(response_addToCart.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertNotEquals(response_removeFromCart.getStatusCode(), HttpStatus.FORBIDDEN);
  
    // item controller
    ResponseEntity<Object> response_getItems = restTemplate.exchange(
      "http://localhost:" + port + "/api/item",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    ResponseEntity<Object> response_getItemById = restTemplate.exchange(
      "http://localhost:" + port + "/api/item/1",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    ResponseEntity<Object> response_getItemsByName = restTemplate.exchange(
      "http://localhost:" + port + "/api/item/name/aName",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    Assertions.assertNotEquals(response_getItems.getStatusCode(), HttpStatus.UNAUTHORIZED);
    Assertions.assertNotEquals(response_getItemById.getStatusCode(), HttpStatus.UNAUTHORIZED);
    Assertions.assertNotEquals(response_getItemsByName.getStatusCode(), HttpStatus.UNAUTHORIZED);
  
    // order controller
    ResponseEntity<Object> response_submit = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/submmit/aName",
      HttpMethod.POST,
      httpEntity,
      Object.class
    );
    ResponseEntity<Object> response_getOrdersForUser = restTemplate.exchange(
      "http://localhost:" + port + "/api/order/history/aName",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    Assertions.assertNotEquals(response_submit.getStatusCode(), HttpStatus.FORBIDDEN);
    Assertions.assertNotEquals(response_getOrdersForUser.getStatusCode(), HttpStatus.UNAUTHORIZED);
  
    // user controller
    ResponseEntity<Object> response_findById = restTemplate.exchange(
      "http://localhost:" + port + "/api/user",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    ResponseEntity<Object> response_findByUserName = restTemplate.exchange(
      "http://localhost:" + port + "/api/user/1",
      HttpMethod.GET,
      httpEntity,
      Object.class
    );
    Assertions.assertNotEquals(response_findById.getStatusCode(), HttpStatus.UNAUTHORIZED);
    Assertions.assertNotEquals(response_findByUserName.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

}

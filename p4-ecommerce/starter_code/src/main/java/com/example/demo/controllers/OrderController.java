package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger("main_logger");
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		try {
      User user = userRepository.findByUsername(username);
      if(user == null) {
        LOGGER.info("{ \"event\": " + "\"[ORDER-CANCELED] username: " + username +  "\", \"source\": \"cartapp\" }");
        return ResponseEntity.notFound().build();
      }
      UserOrder order = UserOrder.createFromCart(user.getCart());
      orderRepository.save(order);
      LOGGER.info("{ \"event\": " + "\"[ORDER-SUCCESS] username: " + username +  "\", \"source\": \"cartapp\" }");
      return ResponseEntity.ok(order);
    } catch (Exception ex) {
      LOGGER.info("{ \"event\": " + "\"[ORDER-FAILED] username: " + username +  "\", \"source\": \"cartapp\" }");
      throw ex;
    }
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}

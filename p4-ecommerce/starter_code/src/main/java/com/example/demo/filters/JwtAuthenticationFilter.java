package com.example.demo.filters;

import com.auth0.jwt.JWT;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Collections.emptyList;

import com.example.demo.configs.SecurityConstants;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  
  private AuthenticationManager authenticationManager;
  
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
  
  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws AuthenticationException {
    try {
      User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
      Collection<GrantedAuthority> roles = new ArrayList<>();
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("authorizedAuthority");
      roles.add(authority);
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        creds.getUsername(),
        creds.getPassword(),
        roles
      );
      Authentication auth = authenticationManager.authenticate(token);
      return auth;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication auth
  ) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User user =
      (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    String username = user.getUsername();
    
    String token = JWT.create()
       .withSubject(username)
       .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
       .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
    
    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}

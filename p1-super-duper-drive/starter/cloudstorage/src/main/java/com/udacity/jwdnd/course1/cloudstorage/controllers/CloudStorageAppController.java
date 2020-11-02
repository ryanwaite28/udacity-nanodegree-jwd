package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserLoginDto;
import com.udacity.jwdnd.course1.cloudstorage.dto.UserSignupDto;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CloudStorageAppController {
  
  @Autowired
  private AuthService authService;
  
  @Autowired
  private UsersService usersService;

  /* GET Methods */

  @GetMapping("/")
  public ModelAndView welcomePage(ModelMap modelMap) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (theUserIsLoggedIn) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
      modelMap.addAttribute("user", user);
      return new ModelAndView("redirect:/home");
    }
    return new ModelAndView("welcome", modelMap);
  }
  
  @GetMapping("/logout")
  public ModelAndView logoutUser(
    RedirectAttributes redirectAttributes
  ) {
    boolean sighedOutSuccessfully = this.authService.logOut();
    if (sighedOutSuccessfully) {
      redirectAttributes.addAttribute("loggedOut", "true");
    }
    return new ModelAndView("redirect:/login");
  }
  
  @GetMapping("/signup")
  public ModelAndView signupPage(
    @ModelAttribute("usernameIsTaken") String usernameIsTaken,
    @ModelAttribute("usersPostError") String usersPostError,
    ModelMap modelMap,
    UserSignupDto userSignupDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (theUserIsLoggedIn) {
      return new ModelAndView("redirect:/home");
    }
    modelMap.addAttribute("usernameIsTaken", usernameIsTaken.equals("") ? null : usernameIsTaken);
    modelMap.addAttribute("usersPostError", usersPostError.equals("") ? null : usersPostError);
    return new ModelAndView("signup");
  }
  
  @GetMapping("/login")
  public ModelAndView loginPage(
    @ModelAttribute("invalidCred") String invalidCred,
    @ModelAttribute("loggedOut") String loggedOut,
    ModelMap modelMap,
    UserLoginDto userLoginDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (theUserIsLoggedIn) {
      return new ModelAndView("redirect:/home");
    }
    modelMap.addAttribute("invalidCred", invalidCred.equals("") ? null : invalidCred);
    modelMap.addAttribute("loggedOut", loggedOut.equals("") ? null : loggedOut);
    return new ModelAndView("login");
  }
  
  /* POST Methods */
  
  @PostMapping("/users")
  public ModelAndView signupUser(
    RedirectAttributes redirectAttributes,
    UserSignupDto userSignupDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (theUserIsLoggedIn) {
      return new ModelAndView("redirect:/home");
    }
    
    User checkUser = this.usersService.getUserByUsername(userSignupDto.getUsername());
    boolean usernameIsTaken = checkUser != null;
    if (usernameIsTaken) {
      redirectAttributes.addAttribute("usernameIsTaken", "true");
      return new ModelAndView("redirect:/signup");
    }
    
    User newUser = this.usersService.signUp(userSignupDto);
    boolean signupWasSuccessful = newUser != null;
    if (signupWasSuccessful) {
      return new ModelAndView("redirect:/home");
    } else {
      userSignupDto.setPassword(null);
      redirectAttributes.addAttribute("usersPostError", "true");
      return new ModelAndView("redirect:/signup");
    }
  }
  
  @PostMapping("/login")
  public ModelAndView loginProcessing(
    ModelMap modelMap,
    UserLoginDto userLoginDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (theUserIsLoggedIn) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
      modelMap.addAttribute("user", user);
      return new ModelAndView("redirect:/home");
    }
    return new ModelAndView("login");
  }
  
  /* PUT Methods */
  
  
  
  /* DELETE Methods */

}

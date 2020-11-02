package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// https://attacomsian.com/blog/spring-boot-custom-error-page
@Controller
public class CustomAppErrorController implements ErrorController {
  
  @Autowired
  private AuthService authService;
  
  @Override
  public String getErrorPath() {
    return "/error";
  }
  
  @RequestMapping("/error")
  public ModelAndView handleError(
    HttpServletRequest request,
    ModelMap modelMap
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    // get error status
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    String errorMessage = "Server Error; something went wrong...";
    
    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      // display specific error page
      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        errorMessage = "Resource not found...";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        errorMessage = "Server Error; something went wrong...";
      } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
        errorMessage = "This action is forbidden...";
      }
    }
  
    modelMap.addAttribute("theUserIsLoggedIn", theUserIsLoggedIn);
    modelMap.addAttribute("errorMessage", errorMessage);
    
    // display generic error
    return new ModelAndView("error");
  }
}

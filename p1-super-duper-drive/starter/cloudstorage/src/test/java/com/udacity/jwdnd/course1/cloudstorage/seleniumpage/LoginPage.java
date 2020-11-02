package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  @FindBy(id = "errorMessage")
  private WebElement errorMessage;
  
  @FindBy(id = "loggedMessage")
  private WebElement loggedMessage;
  
  @FindBy(id = "inputUsername")
  private WebElement inputUsername;
  
  @FindBy(id = "inputPassword")
  private WebElement inputPassword;
  
  @FindBy(id = "login-submit")
  private WebElement loginSubmit;
  
  public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
  
  public void setInputUsernameField(String inputValue) {
    inputUsername.sendKeys(inputValue);
  }
  
  public void setInputPasswordField(String inputValue) {
    inputPassword.sendKeys(inputValue);
  }
  
  public void sendForm() {
    loginSubmit.click();
  }
}

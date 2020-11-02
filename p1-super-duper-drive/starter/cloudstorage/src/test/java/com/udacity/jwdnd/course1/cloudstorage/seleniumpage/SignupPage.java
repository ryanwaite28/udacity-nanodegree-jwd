package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  @FindBy(id = "usernameIsTaken")
  private WebElement usernameIsTaken;
  
  @FindBy(id = "usersPostError")
  private WebElement usersPostError;
  
  @FindBy(id = "inputFirstName")
  private WebElement inputFirstName;
  
  @FindBy(id = "inputLastName")
  private WebElement inputLastName;
  
  @FindBy(id = "inputUsername")
  private WebElement inputUsername;
  
  @FindBy(id = "inputPassword")
  private WebElement inputPassword;
  
  @FindBy(id = "signup-submit")
  private WebElement signupSubmit;
  
  public SignupPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
  
  public void setInputFirstNameField(String inputValue) {
    inputFirstName.sendKeys(inputValue);
  }
  
  public void setInputLastNameField(String inputValue) {
    inputLastName.sendKeys(inputValue);
  }
  
  public void setInputUsernameField(String inputValue) {
    inputUsername.sendKeys(inputValue);
  }
  
  public void setInputPasswordField(String inputValue) {
    inputPassword.sendKeys(inputValue);
  }
  
  public void sendForm() {
    signupSubmit.click();
  }
}

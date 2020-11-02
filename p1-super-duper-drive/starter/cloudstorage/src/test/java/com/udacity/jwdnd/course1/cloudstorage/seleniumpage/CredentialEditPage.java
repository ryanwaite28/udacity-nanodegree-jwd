package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialEditPage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  @FindBy(id = "logoutLink")
  private WebElement logoutLink;
  
  @FindBy(id = "credentialUrl")
  private WebElement credentialUrlField;
  
  @FindBy(id = "credentialUsername")
  private WebElement credentialUsernameField;
  
  @FindBy(id = "credentialPassword")
  private WebElement credentialPasswordField;
  
  @FindBy(id = "credentialSubmitProxy")
  private WebElement credentialSubmitProxy;
  
  public CredentialEditPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
  
  public void setCredentialUrlField(String inputValue) {
    credentialUrlField.clear();
    credentialUrlField.sendKeys(inputValue);
  }
  
  public void setCredentialUsernameField(String inputValue) {
    credentialUsernameField.clear();
    credentialUsernameField.sendKeys(inputValue);
  }
  
  public void setCredentialPasswordField(String inputValue) {
    credentialPasswordField.clear();
    credentialPasswordField.sendKeys(inputValue);
  }
  
  public void logout() {
    logoutLink.click();
  }
  
  public void sendCredentialForm() {
    credentialSubmitProxy.click();
  }
}

package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  public WelcomePage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
}

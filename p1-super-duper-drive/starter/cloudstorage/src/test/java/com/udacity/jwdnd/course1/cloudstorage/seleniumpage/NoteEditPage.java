package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteEditPage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  @FindBy(id = "logoutLink")
  private WebElement logoutLink;
  
  @FindBy(id = "noteTitle")
  private WebElement noteTitleField;
  
  @FindBy(id = "noteDescription")
  private WebElement noteDescriptionField;
  
  @FindBy(id = "noteSubmitProxy")
  private WebElement noteSubmitProxy;
  
  public NoteEditPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
  
  public void setNoteTitleField(String inputValue) {
    noteTitleField.clear();
    noteTitleField.sendKeys(inputValue);
  }
  
  public void setNoteDescriptionField(String inputValue) {
    noteDescriptionField.clear();
    noteDescriptionField.sendKeys(inputValue);
  }
  
  public void logout() {
    logoutLink.click();
  }
  
  public void sendNoteForm() {
    noteSubmitProxy.click();
  }
}

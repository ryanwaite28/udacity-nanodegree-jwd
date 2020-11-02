package com.udacity.jwdnd.course1.cloudstorage.seleniumpage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
  @FindBy(id = "user-main-nav")
  private WebElement userMainNav;
  
  @FindBy(id = "logoutLink")
  private WebElement logoutLink;
  
  @FindBy(id = "nav-notes-tab")
  private WebElement notesTabLink;
  
  @FindBy(id = "nav-credentials-tab")
  private WebElement credsTabLink;
  
  /* Notes */
  
  @FindBy(className = "note-row-item")
  private List<WebElement> noteRows;
  
  @FindBy(id = "openNoteModal")
  private WebElement openNoteModal;
  
  @FindBy(id = "noteTitle")
  private WebElement noteTitleField;
  
  @FindBy(id = "noteDescription")
  private WebElement noteDescriptionField;
  
  @FindBy(id = "noteSubmitProxy")
  private WebElement noteSubmitProxy;
  
  /* Files */
  
  @FindBy(id = "fileUpload")
  private WebElement fileUploadInput;
  
  @FindBy(id = "fileSubmit")
  private WebElement fileSubmit;
  
  @FindBy(className = "file-row-item")
  private List<WebElement> fileRows;
  
  /* Credentials */
  
  @FindBy(className = "credential-row-item")
  private List<WebElement> credentialRows;
  
  @FindBy(id = "openCredentialModal")
  private WebElement openCredentialModal;
  
  @FindBy(id = "credentialUrl")
  private WebElement credentialUrlField;
  
  @FindBy(id = "credentialUsername")
  private WebElement credentialUsernameField;
  
  @FindBy(id = "credentialPassword")
  private WebElement credentialPasswordField;
  
  @FindBy(id = "credentialSubmitProxy")
  private WebElement credentialSubmitProxy;
  
  private WebDriver driver;
  
  /* Constructor */
  
  public HomePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  /* Helper Methods */
  
  public void openNotesTab() {
    notesTabLink.click();
  }
  
  public void openCredsTab() {
    credsTabLink.click();
  }
  
  public void logout() {
    logoutLink.sendKeys(Keys.ENTER);
  }
  
  public boolean pageHasUserMainNav() {
    boolean hasNav = userMainNav != null;
    return hasNav;
  }
  
  /* Notes */
  
  public List<WebElement> getNoteRows() {
    return noteRows;
  }
  
  public String getNoteTitleByNoteId(int noteId) {
    WebElement titleTableData = driver.findElement(By.id("note-" + noteId + "-title"));
    String innerText = titleTableData == null ? null : titleTableData.getText();
    return innerText;
  }
  
  public String getNoteDescriptionByNoteId(int noteId) {
    WebElement descriptionTableData = driver.findElement(By.id("note-" + noteId + "-description"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public String getNoteTitleByIndex(int index) {
    WebElement noteRow = noteRows.get(index);
    WebElement titleTableData = noteRow.findElement(By.className("note-title"));
    String innerText = titleTableData == null ? null : titleTableData.getText();
    return innerText;
  }
  
  public String getNoteDescriptionByIndex(int index) {
    WebElement noteRow = noteRows.get(index);
    WebElement descriptionTableData = noteRow.findElement(By.className("note-description"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public WebElement getNoteEditLinkByNoteId(int noteId) {
    return driver.findElement(By.id("note-" + noteId + "-edit-link"));
  }
  
  public WebElement getNoteDeleteBtnByNoteId(int noteId) {
    return driver.findElement(By.id("note-" + noteId + "-delete-btn"));
  }
  
  public WebElement getNoteEditLinkByIndex(int index) {
    WebElement noteRow = noteRows.get(index);
    return noteRow.findElement(By.className("note-edit-link"));
  }
  
  public WebElement getNoteDeleteBtnByIndex(int index) {
    WebElement noteRow = noteRows.get(index);
    return noteRow.findElement(By.className("note-delete-btn"));
  }
  
  public void openNewNoteModal() {
    openNoteModal.click();
  }
  
  public void setNoteTitleField(String inputValue) {
    noteTitleField.sendKeys(inputValue);
  }
  
  public void setNoteDescriptionField(String inputValue) {
    noteDescriptionField.sendKeys(inputValue);
  }
  
  public void sendNoteForm() {
    noteSubmitProxy.click();
  }
  
  /* Credentials */
  
  public List<WebElement> getCredentialRows() {
    return credentialRows;
  }
  
  public String getCredUrlByCredId(int credId) {
    WebElement titleTableData = driver.findElement(By.id("credential-" + credId + "-url"));
    String innerText = titleTableData == null ? null : titleTableData.getText();
    return innerText;
  }
  
  public String getCredUsernameByCredId(int credId) {
    WebElement descriptionTableData = driver.findElement(By.id("credential-" + credId + "-username"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public String getCredPasswordByCredId(int credId) {
    WebElement descriptionTableData = driver.findElement(By.id("credential-" + credId + "-password"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public String getCredUrlByIndex(int index) {
    WebElement credentialRow = credentialRows.get(index);
    WebElement titleTableData = driver.findElement(By.className("credential-url"));
    String innerText = titleTableData == null ? null : titleTableData.getText();
    return innerText;
  }
  
  public String getCredUsernameByIndex(int index) {
    WebElement credentialRow = credentialRows.get(index);
    WebElement descriptionTableData = driver.findElement(By.className("credential-username"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public String getCredPasswordByIndex(int index) {
    WebElement credentialRow = credentialRows.get(index);
    WebElement descriptionTableData = driver.findElement(By.className("credential-password"));
    String innerText = descriptionTableData == null ? null : descriptionTableData.getText();
    return innerText;
  }
  
  public WebElement getCredEditLinkByCredId(int credId) {
    return driver.findElement(By.id("credential-" + credId + "-edit-link"));
  }
  
  public WebElement getCredDeleteBtnByCredId(int credId) {
    return driver.findElement(By.id("credential-" + credId + "-delete-btn"));
  }
  
  public WebElement getCredEditLinkByIndex(int index) {
    WebElement credentialRow = credentialRows.get(index);
    return credentialRow.findElement(By.className("credential-edit-link"));
  }
  
  public WebElement getCredDeleteBtnByIndex(int index) {
    WebElement credentialRow = credentialRows.get(index);
    return credentialRow.findElement(By.className("credential-delete-btn"));
  }
  
  public void openNewCredentialModal() {
    openCredentialModal.click();
  }
  
  public void setCredentialUrlField(String inputValue) {
    credentialUrlField.sendKeys(inputValue);
  }
  
  public void setCredentialUsernameField(String inputValue) {
    credentialUsernameField.sendKeys(inputValue);
  }
  
  public void setCredentialPasswordField(String inputValue) {
    credentialPasswordField.sendKeys(inputValue);
  }
  
  public void sendCredentialForm() {
    credentialSubmitProxy.click();
  }
}

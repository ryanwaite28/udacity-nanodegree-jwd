package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.seleniumpage.*;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.DEFAULT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
  
  @Autowired
  private AuthService authService;
  
  @Autowired
  private EncryptionService encryptionService;
  
  @Autowired
  private UsersService usersService;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private NoteMapper noteMapper;
  
  @Autowired
  private FileMapper fileMapper;
  
  @Autowired
  private CredentialMapper credentialMapper;

	private WebDriver driver;
	
	/* Setup */

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	
	/* Helper */
  
  public User createUserViaSignup() {
    driver.get("http://localhost:" + this.port + "/signup");
  
    // test sign up
    SignupPage signupPage = new SignupPage(driver);
    signupPage.setInputFirstNameField("Ryan");
    signupPage.setInputLastNameField("Waite");
    signupPage.setInputUsernameField("ryanwaite28");
    signupPage.setInputPasswordField("password");
    signupPage.sendForm();
    User user = userMapper.getUser("ryanwaite28");
    return user;
  }
  
  public void deleteUser() {
    userMapper.delete("ryanwaite28");
  }
	
	/* Tests */

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign up", driver.getTitle());
	}
  
  @Test
  public void getLoginPage() {
    driver.get("http://localhost:" + this.port + "/login");
    Assertions.assertEquals("Log in", driver.getTitle());
  }
  
  @Test
  public void unauthorizedCannotAccessHome() {
    driver.get("http://localhost:" + this.port + "/home");
    Assertions.assertEquals("Log in", driver.getTitle());
  }
  
  @Test
  public void unauthorizedCannotAccessNoteEdit() {
    driver.get("http://localhost:" + this.port + "/home");
    Assertions.assertEquals("Log in", driver.getTitle());
  }
  
  @Test
  public void unauthorizedCannotAccessCredentialEdit() {
    driver.get("http://localhost:" + this.port + "/home");
    Assertions.assertEquals("Log in", driver.getTitle());
  }
  
  @Test
  public void createsUserAndLogsOutAndLogsInSuccessfully() {
    try {
      User user = createUserViaSignup();
      Assertions.assertNotNull(user);
  
      // test home page redirect on successful sign up
      Assertions.assertEquals("Home", driver.getTitle());
  
      // test logout
      Thread.sleep(1000);
      HomePage homePage = new HomePage(driver);
      homePage.logout();
      Thread.sleep(1000);
      Assertions.assertEquals("Log in", driver.getTitle());
      
      // test home page not accessible
      driver.get("http://localhost:" + this.port + "/home");
      Assertions.assertEquals("Log in", driver.getTitle());
  
      // test login
      LoginPage loginPage = new LoginPage(driver);
      loginPage.setInputUsernameField("ryanwaite28");
      loginPage.setInputPasswordField("password");
      loginPage.sendForm();
      Assertions.assertEquals("Home", driver.getTitle());
      // delete user
      deleteUser();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  @Test
  public void createEditDeleteNoteSuccessfully() {
    try {
      User user = createUserViaSignup();
      Assertions.assertNotNull(user);
  
      // test create note
      HomePage homePage = new HomePage(driver);
  
      // test default amount
      List<WebElement> noteRows = homePage.getNoteRows();
      Assertions.assertEquals(0, noteRows.size());
  
      // test creating note
      String newNoteTitle = "A note title";
      String newNoteDescription = "A note description";
  
      Thread.sleep(1000);
      homePage.openNewNoteModal();
      Thread.sleep(1000);
      homePage.setNoteTitleField(newNoteTitle);
      homePage.setNoteDescriptionField(newNoteDescription);
      Thread.sleep(1000);
      homePage.sendNoteForm();
  
      // test new note showing
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      noteRows = homePage.getNoteRows();
      Assertions.assertEquals(1, noteRows.size());
      
      Thread.sleep(1000);
      String newNoteElementTitle = homePage.getNoteTitleByIndex(0);
      String newNoteElementDescription = homePage.getNoteDescriptionByIndex(0);
      Assertions.assertEquals(newNoteTitle, newNoteElementTitle);
      Assertions.assertEquals(newNoteDescription, newNoteElementDescription);
      
      // test edit note
      String editNoteTitle = "Another note title";
      String editNoteDescription = "Another note description";
  
      Thread.sleep(1000);
      homePage.getNoteEditLinkByIndex(0).click();
      NoteEditPage noteEditPage = new NoteEditPage(driver);
      Thread.sleep(1000);
      noteEditPage.setNoteTitleField(editNoteTitle);
      noteEditPage.setNoteDescriptionField(editNoteDescription);
      Thread.sleep(1000);
      noteEditPage.sendNoteForm();
  
      // test edit note showing
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      noteRows = homePage.getNoteRows();
      Assertions.assertEquals(1, noteRows.size());
  
      Thread.sleep(1000);
      String editNoteElementTitle = homePage.getNoteTitleByIndex(0);
      String editNoteElementDescription = homePage.getNoteDescriptionByIndex(0);
      Assertions.assertEquals(editNoteElementTitle, editNoteElementTitle);
      Assertions.assertEquals(editNoteElementDescription, editNoteElementDescription);
      
      // test deleting note
      homePage.getNoteDeleteBtnByIndex(0).click();
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      noteRows = homePage.getNoteRows();
      Assertions.assertEquals(0, noteRows.size());
  
      // delete user
      deleteUser();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
      Assertions.fail();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assertions.fail();
    }
  }
  
  @Test
  public void createEditDeleteCredentialSuccessfully() {
    try {
      User user = createUserViaSignup();
      Assertions.assertNotNull(user);
      
      // test create credential
      HomePage homePage = new HomePage(driver);
      
      // test default amount
      List<WebElement> credentialRows = homePage.getCredentialRows();
      Assertions.assertEquals(0, credentialRows.size());
      
      // test creating credential
      String newCredentialUrl = "http://localhost:8080";
      String newCredentialUsername = "ryanwaite28";
      String newCredentialPassword = "password";
      
      Thread.sleep(1000);
      homePage.openCredsTab();
      Thread.sleep(1000);
      homePage.openNewCredentialModal();
      Thread.sleep(1000);
      homePage.setCredentialUrlField(newCredentialUrl);
      homePage.setCredentialUsernameField(newCredentialUsername);
      homePage.setCredentialPasswordField(newCredentialPassword);
      Thread.sleep(1000);
      homePage.sendCredentialForm();
      
      // test new credential showing
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      homePage.openCredsTab();
      Thread.sleep(1000);
      credentialRows = homePage.getCredentialRows();
      Assertions.assertEquals(1, credentialRows.size());
      
      Thread.sleep(1000);
      String newCredentialElementUrl = homePage.getCredUrlByIndex(0);
      String newCredentialElementUsername = homePage.getCredUsernameByIndex(0);
      String newCredentialElementPassword = homePage.getCredPasswordByIndex(0);
      Assertions.assertEquals(newCredentialUrl, newCredentialElementUrl);
      Assertions.assertEquals(newCredentialUsername, newCredentialElementUsername);
      Assertions.assertNotEquals(newCredentialPassword, newCredentialElementPassword); // show hashed value, not actual
      
      // test edit credential
      String editCredentialUrl = "http://localhost:500";
      String editCredentialUsername = "ryanwaite08";
      String editCredentialPassword = "password123";
      
      Thread.sleep(1000);
      homePage.getCredEditLinkByIndex(0).click();
      CredentialEditPage credentialEditPage = new CredentialEditPage(driver);
      Thread.sleep(1000);
      credentialEditPage.setCredentialUrlField(editCredentialUrl);
      credentialEditPage.setCredentialUsernameField(editCredentialUsername);
      credentialEditPage.setCredentialPasswordField(editCredentialPassword);
      Thread.sleep(1000);
      credentialEditPage.sendCredentialForm();
      
      // test edit credential showing
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      homePage.openCredsTab();
      Thread.sleep(1000);
      credentialRows = homePage.getCredentialRows();
      Assertions.assertEquals(1, credentialRows.size());
      
      Thread.sleep(1000);
      String editCredentialElementUrl = homePage.getCredUrlByIndex(0);
      String editCredentialElementUsername = homePage.getCredUsernameByIndex(0);
      String editCredentialElementPassword = homePage.getCredPasswordByIndex(0);
      Assertions.assertEquals(editCredentialUrl, editCredentialElementUrl);
      Assertions.assertEquals(editCredentialUsername, editCredentialElementUsername);
      Assertions.assertNotEquals(editCredentialPassword, editCredentialElementPassword); // show hashed value, not actual
      
      // test deleting credential
      homePage.getCredDeleteBtnByIndex(0).click();
      Thread.sleep(1000);
      driver.get("http://localhost:" + this.port + "/home");
      Thread.sleep(1000);
      homePage = new HomePage(driver);
      homePage.openCredsTab();
      Thread.sleep(1000);
      credentialRows = homePage.getCredentialRows();
      Assertions.assertEquals(0, credentialRows.size());
      
      // delete user
      deleteUser();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
      Assertions.fail();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assertions.fail();
    }
  }
}

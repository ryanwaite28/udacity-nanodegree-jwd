package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.dto.UserSignupDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
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
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UsersController {
  
  @Autowired
  private AuthService authService;
  
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

  /* GET Methods */
  
  @GetMapping("/results")
  public ModelAndView actionResults(
    ModelMap modelMap,
    @ModelAttribute("noteCreateSuccess") final String noteCreateSuccess,
    @ModelAttribute("noteUpdateSuccess") final String noteUpdateSuccess,
    @ModelAttribute("noteDeleteSuccess") final String noteDeleteSuccess,
    @ModelAttribute("fileUploadSuccess") final String fileUploadSuccess,
    @ModelAttribute("fileDeleteSuccess") final String fileDeleteSuccess,
    @ModelAttribute("credentialCreateSuccess") final String credentialCreateSuccess,
    @ModelAttribute("credentialUpdateSuccess") final String credentialUpdateSuccess,
    @ModelAttribute("credentialDeleteSuccess") final String credentialDeleteSuccess
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    modelMap.addAttribute("theUserIsLoggedIn", theUserIsLoggedIn);
  
    modelMap.addAttribute("noteCreateSuccess", noteCreateSuccess.equals("") ? null : noteCreateSuccess);
    modelMap.addAttribute("noteUpdateSuccess", noteUpdateSuccess.equals("") ? null : noteUpdateSuccess);
    modelMap.addAttribute("noteDeleteSuccess", noteDeleteSuccess.equals("") ? null : noteDeleteSuccess);
    modelMap.addAttribute("fileUploadSuccess", fileUploadSuccess.equals("") ? null : fileUploadSuccess);
    modelMap.addAttribute("fileDeleteSuccess", fileDeleteSuccess.equals("") ? null : fileDeleteSuccess);
    modelMap.addAttribute("credentialCreateSuccess", credentialCreateSuccess.equals("") ? null : credentialCreateSuccess);
    modelMap.addAttribute("credentialUpdateSuccess", credentialUpdateSuccess.equals("") ? null : credentialUpdateSuccess);
    modelMap.addAttribute("credentialDeleteSuccess", credentialDeleteSuccess.equals("") ? null : credentialDeleteSuccess);
  
    return new ModelAndView("results");
  }
  
  @GetMapping("/home")
  public ModelAndView userHomePage(
    ModelMap modelMap,
    NoteDto newNoteDto,
    CredentialDto newCredentialDto,
    @ModelAttribute("noteUpdateError") final String noteUpdateError,
    @ModelAttribute("noteDeleteError") final String noteDeleteError,
    @ModelAttribute("filePostError") final String filePostError,
    @ModelAttribute("fileDownloadError") final String fileDownloadError,
    @ModelAttribute("credentialUpdateError") final String credentialUpdateError,
    @ModelAttribute("credentialDeleteError") final String credentialDeleteError
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    Integer userId = user.getUserId();
    
    List<Note> notesList = noteMapper.getNotesByUser(userId);
    List<File> filesList = fileMapper.getFilesByUser(userId);
    List<Credential> credentialsList = credentialMapper.getByUserId(userId);
    
    modelMap.addAttribute("user", user);
    modelMap.addAttribute("newNoteDto", newNoteDto);
    modelMap.addAttribute("newCredentialDto", newCredentialDto);
    modelMap.addAttribute("notesList", notesList);
    modelMap.addAttribute("filesList", filesList);
    modelMap.addAttribute("credentialsList", credentialsList);
  
    modelMap.addAttribute("noteUpdateError", noteUpdateError.equals("") ? null : noteUpdateError);
    modelMap.addAttribute("noteDeleteError", noteDeleteError.equals("") ? null : noteDeleteError);
    modelMap.addAttribute("filePostError", filePostError.equals("") ? null : filePostError);
    modelMap.addAttribute("fileDownloadError", fileDownloadError.equals("") ? null : fileDownloadError);
    modelMap.addAttribute("credentialUpdateError", credentialUpdateError.equals("") ? null : credentialUpdateError);
    modelMap.addAttribute("credentialDeleteError", credentialDeleteError.equals("") ? null : credentialDeleteError);
    
    return new ModelAndView("home");
  }

  /* POST Methods */
  
  

  /* PUT Methods */



  /* DELETE Methods */

}

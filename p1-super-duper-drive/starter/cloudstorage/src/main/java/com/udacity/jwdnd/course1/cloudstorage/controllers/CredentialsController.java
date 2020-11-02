package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {
  
  @Autowired
  private AuthService authService;
  
  @Autowired
  private HashService hashService;
  
  @Autowired
  private EncryptionService encryptionService;
  
  @Autowired
  private UsersService usersService;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private CredentialMapper credentialMapper;

  /* GET Methods */
  
  @GetMapping("/credentials/{id}/edit")
  public ModelAndView getNotePage(
    @PathVariable("id") Integer credentialId,
    ModelMap modelMap,
    CredentialDto credentialDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    Credential credential = credentialMapper.getById(credentialId);
    if (credential == null) {
      modelMap.addAttribute("credentialGetError", "No credential found by id: " + credentialId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = credential.getUserId() != user.getUserId();
    if (notOwner) {
      modelMap.addAttribute("credentialGetError", "You do not own this credential");
      return new ModelAndView("note", modelMap);
    }
    String pswrd = this.encryptionService.decryptValue(
      credential.getPassword(),
      credential.getSalt()
    );
    credentialDto.setCredentialId(credential.getCredentialId());
    credentialDto.setUserId(credential.getUserId());
    credentialDto.setUrl(credential.getUrl());
    credentialDto.setUsername(credential.getUsername());
    credentialDto.setPassword(pswrd);
    credentialDto.setSalt(credential.getSalt());
    modelMap.remove("noteGetError");
    return new ModelAndView("credential", modelMap);
  }

  /* POST Methods */
  
  @PostMapping("/credentials")
  public ModelAndView createNote(
    CredentialDto credentialDto,
    RedirectAttributes redirectAttributes
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    String salt = this.hashService.generateSalt();
    String pswrd = credentialDto.getPassword();
    String hashedPassword = this.encryptionService.encryptValue(pswrd, salt);
    Credential newCredential = new Credential(
      null,
      user.getUserId(),
      credentialDto.getUrl(),
      credentialDto.getUsername(),
      hashedPassword,
      salt
    );
    credentialMapper.insert(newCredential);
    redirectAttributes.addAttribute("credentialCreateSuccess", "true");
    return new ModelAndView("redirect:/results");
  }

  /* PUT Methods */
  
  @PutMapping("/credentials/{id}")
  public ModelAndView updateCredential(
    @PathVariable("id") Integer credentialId,
    RedirectAttributes redirectAttributes,
    CredentialDto credentialDto
  ) {
    Credential credential = credentialMapper.getById(credentialId);
    if (credential == null) {
      redirectAttributes.addAttribute("credentialUpdateError", "No credential found by id: " + credentialId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = credential.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("credentialUpdateError", "You do not own this credential");
      return new ModelAndView("redirect:/home");
    }
    String salt = credential.getSalt();
    String pswrd = credentialDto.getPassword();
    String hashedPassword = this.encryptionService.encryptValue(pswrd, salt);
    Credential updatesCredentialDto = new Credential(
      credentialId,
      user.getUserId(),
      credentialDto.getUrl(),
      credentialDto.getUsername(),
      hashedPassword,
      salt
    );
    credentialMapper.update(updatesCredentialDto);
    redirectAttributes.addAttribute("credentialUpdateSuccess", "true");
    return new ModelAndView("redirect:/results");
  }

  /* DELETE Methods */
  
  @DeleteMapping("/credentials/{id}")
  public ModelAndView deleteCredential(
    @PathVariable("id") Integer credentialId,
    RedirectAttributes redirectAttributes,
    NoteDto noteDto
  ) {
    Credential credential = credentialMapper.getById(credentialId);
    if (credential == null) {
      redirectAttributes.addAttribute("credentialDeleteError", "No credential found by id: " + credentialId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = credential.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("credentialDeleteError", "You do not own this credential");
      return new ModelAndView("redirect:/home");
    }
    credentialMapper.delete(credentialId);
    redirectAttributes.addAttribute("credentialDeleteSuccess", "true");
    return new ModelAndView("redirect:/results");
  }
}

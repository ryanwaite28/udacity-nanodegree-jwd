package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;

@Controller
public class FilesController {
  
  @Autowired
  private AuthService authService;
  
  @Autowired
  private UsersService usersService;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private FileMapper fileMapper;

  /* GET Methods */
  
  @GetMapping("/files/{id}/download")
  public ModelAndView createNote(
    @PathVariable("id") Integer fileId,
    RedirectAttributes redirectAttributes,
    HttpServletResponse response
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    File file = fileMapper.getFile(fileId);
    if (file == null) {
      redirectAttributes.addAttribute("fileDownloadError", "No file found by id: " + fileId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = file.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("fileDownloadError", "You do not own this file");
      return new ModelAndView("redirect:/home");
    }
    response.setStatus(200);
    response.setContentType(file.getContentType());
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
    try {
      response.getOutputStream().write(file.getFileData());
      response.flushBuffer();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addAttribute("fileDownloadError", "Could not download file, unexpected error...");
      return new ModelAndView("redirect:/home");
    }
  }

  /* POST Methods */
  
  @PostMapping("/files")
  public ModelAndView uploadFile(
    @RequestParam("fileUpload") MultipartFile fileUpload,
    RedirectAttributes redirectAttributes
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    String fileName = fileUpload.getOriginalFilename().replaceAll(" ", "-");
    File fileExistsByName = this.fileMapper.getFileByName(fileName);
    if (fileExistsByName != null) {
      redirectAttributes.addAttribute("filePostError", "Could not upload: file already exists by name: " + fileName);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    String fileSize = String.valueOf(fileUpload.getSize());
    byte[] fileDataRaw;
    Blob fileData;
    try {
      fileDataRaw = fileUpload.getBytes();
      fileData = new SerialBlob(fileDataRaw);
    } catch (Exception ex) {
      redirectAttributes.addAttribute("filePostError", "Could not upload: file was empty/no contents");
      return new ModelAndView("redirect:/home");
    }
    File file = new File(
      null,
      fileName,
      fileUpload.getContentType(),
      fileSize,
      user.getUserId(),
      fileDataRaw
    );
    fileMapper.insert(file);
    redirectAttributes.addAttribute("fileUploadSuccess", "true");
    return new ModelAndView("redirect:/results");
  }

  /* PUT Methods */



  /* DELETE Methods */
  
  @DeleteMapping("/files/{id}")
  public ModelAndView deleteFile(
    @PathVariable("id") Integer fileId,
    RedirectAttributes redirectAttributes
  ) {
    File file = fileMapper.getFile(fileId);
    if (file == null) {
      redirectAttributes.addAttribute("fileDeleteError", "No file found by id: " + fileId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = file.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("fileDeleteError", "You do not own this file");
      return new ModelAndView("redirect:/home");
    }
    fileMapper.delete(fileId);
    redirectAttributes.addAttribute("fileDeleteSuccess", "true");
    return new ModelAndView("redirect:/results");
  }
}

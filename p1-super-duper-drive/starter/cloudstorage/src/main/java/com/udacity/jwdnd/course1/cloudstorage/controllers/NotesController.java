package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.dto.UserLoginDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
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
public class NotesController {
  
  @Autowired
  private AuthService authService;
  
  @Autowired
  private UsersService usersService;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private NoteMapper noteMapper;

  /* GET Methods */
  
  @GetMapping("/notes/{id}/edit")
  public ModelAndView getNotePage(
    @PathVariable("id") Integer noteId,
    ModelMap modelMap,
    NoteDto noteDto
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    Note note = noteMapper.getNote(noteId);
    if (note == null) {
      modelMap.addAttribute("noteGetError", "No note found by id: " + noteId);
      return new ModelAndView("note", modelMap);
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = note.getUserId() != user.getUserId();
    if (notOwner) {
      modelMap.addAttribute("noteGetError", "You do not own this note");
      return new ModelAndView("note", modelMap);
    }
    noteDto.setTitle(note.getNoteTitle());
    noteDto.setDescription(note.getNoteDescription());
    noteDto.setNoteId(note.getNoteId());
    noteDto.setUserId(note.getUserId());
    modelMap.remove("noteGetError");
    return new ModelAndView("note", modelMap);
  }

  /* POST Methods */
  
  @PostMapping("/notes")
  public ModelAndView createNote(
    NoteDto noteDto,
    RedirectAttributes redirectAttributes
  ) {
    boolean theUserIsLoggedIn = this.authService.isUserLoggedIn();
    if (!theUserIsLoggedIn) {
      return new ModelAndView("redirect:/login");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    Note newNote = new Note(
      null,
      noteDto.getTitle(),
      noteDto.getDescription(),
      user.getUserId()
    );
    noteMapper.insert(newNote);
    redirectAttributes.addAttribute("noteCreateSuccess", "true");
    return new ModelAndView("redirect:/results");
  }

  /* PUT Methods */
  
  @PutMapping("/notes/{id}")
  public ModelAndView updateNote(
    @PathVariable("id") Integer noteId,
    RedirectAttributes redirectAttributes,
    NoteDto noteDto
  ) {
    Note note = noteMapper.getNote(noteId);
    if (note == null) {
      redirectAttributes.addAttribute("noteUpdateError", "No note found by id: " + noteId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = note.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("noteUpdateError", "You do not own this note");
      return new ModelAndView("redirect:/home");
    }
    Note updatesNote = new Note(
      noteId,
      noteDto.getTitle(),
      noteDto.getDescription(),
      user.getUserId()
    );
    noteMapper.update(updatesNote);
    redirectAttributes.addAttribute("noteUpdateSuccess", "true");
    return new ModelAndView("redirect:/results");
  }

  /* DELETE Methods */
  
  @DeleteMapping("/notes/{id}")
  public ModelAndView deleteNote(
    @PathVariable("id") Integer noteId,
    RedirectAttributes redirectAttributes,
    NoteDto noteDto
  ) {
    Note note = noteMapper.getNote(noteId);
    if (note == null) {
      redirectAttributes.addAttribute("noteDeleteError", "No note found by id: " + noteId);
      return new ModelAndView("redirect:/home");
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = this.usersService.getUserByUsername(auth.getPrincipal().toString());
    boolean notOwner = note.getUserId() != user.getUserId();
    if (notOwner) {
      redirectAttributes.addAttribute("noteDeleteError", "You do not own this note");
      return new ModelAndView("redirect:/home");
    }
    noteMapper.delete(noteId);
    redirectAttributes.addAttribute("noteDeleteSuccess", "true");
    return new ModelAndView("redirect:/results");
  }
}

package com.udacity.jwdnd.course1.cloudstorage.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NoteDto {
  @NotNull
  @NotEmpty
  private String title;
  
  @NotNull
  @NotEmpty
  private String description;
  
  private Integer noteId;
  private Integer userId;
  
  public Integer getNoteId() {
    return noteId;
  }
  
  public void setNoteId(Integer noteId) {
    this.noteId = noteId;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}

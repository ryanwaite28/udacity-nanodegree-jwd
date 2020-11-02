package com.udacity.jwdnd.course1.cloudstorage.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

public class FileDto {
  @NotNull
  @NotEmpty
  private String filename;
  
  @NotNull
  @NotEmpty
  private String contenttype;
  
  @NotNull
  @NotEmpty
  private String filezise;
  
  @NotNull
  @NotEmpty
  private Blob filedata;
  
  private Integer userId;
  private Integer fileId;
  
  public String getFilename() {
    return filename;
  }
  
  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  public String getContenttype() {
    return contenttype;
  }
  
  public void setContenttype(String contenttype) {
    this.contenttype = contenttype;
  }
  
  public String getFilezise() {
    return filezise;
  }
  
  public void setFilezise(String filezise) {
    this.filezise = filezise;
  }
  
  public Blob getFiledata() {
    return filedata;
  }
  
  public void setFiledata(Blob filedata) {
    this.filedata = filedata;
  }
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
  
  public Integer getFileId() {
    return fileId;
  }
  
  public void setFileId(Integer fileId) {
    this.fileId = fileId;
  }
}

package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "schedule")
@Table(name = "schedules")
public class ScheduleEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  private LocalDate date;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduleEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<ScheduledActivityEntity> scheduledActivityEntityList = new ArrayList<ScheduledActivityEntity>();
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduleEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<ScheduledEmployeeEntity> scheduledEmployeeEntityList = new ArrayList<ScheduledEmployeeEntity>();
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduleEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<ScheduledPetEntity> scheduledPetEntityList = new ArrayList<ScheduledPetEntity>();
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public LocalDate getDate() {
    return date;
  }
  
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  public List<ScheduledActivityEntity> getScheduledActivityEntityList() {
    return scheduledActivityEntityList;
  }
  
  public void setScheduledActivityEntityList(List<ScheduledActivityEntity> scheduledActivityEntityList) {
    this.scheduledActivityEntityList = scheduledActivityEntityList;
  }
  
  public List<ScheduledEmployeeEntity> getScheduledEmployeeEntityList() {
    return scheduledEmployeeEntityList;
  }
  
  public void setScheduledEmployeeEntityList(List<ScheduledEmployeeEntity> scheduledEmployeeEntityList) {
    this.scheduledEmployeeEntityList = scheduledEmployeeEntityList;
  }
  
  public List<ScheduledPetEntity> getScheduledPetEntityList() {
    return scheduledPetEntityList;
  }
  
  public void setScheduledPetEntityList(List<ScheduledPetEntity> scheduledPetEntityList) {
    this.scheduledPetEntityList = scheduledPetEntityList;
  }
}

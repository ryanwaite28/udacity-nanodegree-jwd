package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import javax.persistence.*;

@Entity(name = "scheduled_activity")
@Table(name = "scheduled_activities")
public class ScheduledActivityEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private ScheduleEntity scheduleEntity;
  
  private EmployeeSkill employeeSkill;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public ScheduleEntity getScheduleEntity() {
    return scheduleEntity;
  }
  
  public void setScheduleEntity(ScheduleEntity scheduleEntity) {
    this.scheduleEntity = scheduleEntity;
  }
  
  public EmployeeSkill getEmployeeSkill() {
    return employeeSkill;
  }
  
  public void setEmployeeSkill(EmployeeSkill employeeSkill) {
    this.employeeSkill = employeeSkill;
  }
}

package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;

@Entity(name = "scheduled_employee")
@Table(name = "scheduled_employees")
public class ScheduledEmployeeEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private ScheduleEntity scheduleEntity;
  
  @ManyToOne
  private EmployeeEntity employeeEntity;
  
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
  
  public EmployeeEntity getEmployeeEntity() {
    return employeeEntity;
  }
  
  public void setEmployeeEntity(EmployeeEntity employeeEntity) {
    this.employeeEntity = employeeEntity;
  }
}

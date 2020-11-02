package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.DayOfWeek;

@Entity(name = "employee_availability")
@Table(name = "employee_availabilities")
public class EmployeeAvailableEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private EmployeeEntity employeeEntity;
  
  private DayOfWeek dayAvailable;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public EmployeeEntity getEmployeeEntity() {
    return employeeEntity;
  }
  
  public void setEmployeeEntity(EmployeeEntity employeeEntity) {
    this.employeeEntity = employeeEntity;
  }
  
  public DayOfWeek getDayAvailable() {
    return dayAvailable;
  }
  
  public void setDayAvailable(DayOfWeek dayAvailable) {
    this.dayAvailable = dayAvailable;
  }
}

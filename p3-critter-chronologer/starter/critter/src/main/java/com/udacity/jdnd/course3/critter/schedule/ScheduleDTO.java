package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
  private Long id;
  private LocalDate date;
  private List<Long> employeeIds = new ArrayList<Long>();
  private List<Long> petIds = new ArrayList<Long>();
  private Set<EmployeeSkill> activities = new HashSet<EmployeeSkill>();
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public List<Long> getEmployeeIds() {
      return employeeIds;
  }

  public void setEmployeeIds(List<Long> employeeIds) {
      this.employeeIds = employeeIds;
  }

  public List<Long> getPetIds() {
      return petIds;
  }

  public void setPetIds(List<Long> petIds) {
      this.petIds = petIds;
  }

  public LocalDate getDate() {
      return date;
  }

  public void setDate(LocalDate date) {
      this.date = date;
  }

  public Set<EmployeeSkill> getActivities() {
      return activities;
  }

  public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}

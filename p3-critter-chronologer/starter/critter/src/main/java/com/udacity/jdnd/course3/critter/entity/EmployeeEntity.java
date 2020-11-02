package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "employee")
@Table(name = "employees")
public class EmployeeEntity extends UserEntity {
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<EmployeeSkillEntity> skillEntityList = new ArrayList<EmployeeSkillEntity>();
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<EmployeeAvailableEntity> employeeAvailableEntityList = new ArrayList<EmployeeAvailableEntity>();
  
  public List<EmployeeSkillEntity> getSkillEntityList() {
    return skillEntityList;
  }
  
  public void setSkillEntityList(List<EmployeeSkillEntity> skillEntityList) {
    this.skillEntityList = skillEntityList;
  }
  
  public List<EmployeeAvailableEntity> getEmployeeAvailableEntityList() {
    return employeeAvailableEntityList;
  }
  
  public void setEmployeeAvailableEntityList(List<EmployeeAvailableEntity> employeeAvailableEntityList) {
    this.employeeAvailableEntityList = employeeAvailableEntityList;
  }
}

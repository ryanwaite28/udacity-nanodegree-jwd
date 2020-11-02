package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity(name = "employee_skill")
@Table(name = "employee_skills")
public class EmployeeSkillEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private EmployeeEntity employeeEntity;
  
  private EmployeeSkill skill;
  
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
  
  public EmployeeSkill getSkill() {
    return skill;
  }
  
  public void setSkill(EmployeeSkill skill) {
    this.skill = skill;
  }
}

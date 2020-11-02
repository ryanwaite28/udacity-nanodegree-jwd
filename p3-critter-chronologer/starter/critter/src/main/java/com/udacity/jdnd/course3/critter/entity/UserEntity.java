package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity(name = "user")
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @Nationalized
  private String name;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}

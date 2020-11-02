package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "pet")
@Table(name = "pets")
public class PetEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @Nationalized
  private String name;
  
  private PetType type;
  
  @ManyToOne
  private CustomerEntity customerEntity;
  
  private LocalDate birthDate;
  
  private String notes;
  
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
  
  public PetType getType() {
    return type;
  }
  
  public void setType(PetType type) {
    this.type = type;
  }
  
  public CustomerEntity getCustomerEntity() {
    return customerEntity;
  }
  
  public void setCustomerEntity(CustomerEntity customerEntity) {
    this.customerEntity = customerEntity;
  }
  
  public LocalDate getBirthDate() {
    return birthDate;
  }
  
  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
  
  public String getNotes() {
    return notes;
  }
  
  public void setNotes(String notes) {
    this.notes = notes;
  }
}

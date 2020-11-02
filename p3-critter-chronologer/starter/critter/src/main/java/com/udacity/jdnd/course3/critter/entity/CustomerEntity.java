package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
@Table(name = "customers")
public class CustomerEntity extends UserEntity {
  private String phoneNumber;
  
  private String notes;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerEntity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<PetEntity> pets = new ArrayList<PetEntity>();
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public String getNotes() {
    return notes;
  }
  
  public void setNotes(String notes) {
    this.notes = notes;
  }
  
  public List<PetEntity> getPets() {
    return pets;
  }
  
  public void setPets(List<PetEntity> pets) {
    this.pets = pets;
  }
}

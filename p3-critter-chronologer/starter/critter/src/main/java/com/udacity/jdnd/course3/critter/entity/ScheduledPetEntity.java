package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;

@Entity(name = "scheduled_pet")
@Table(name = "scheduled_pets")
public class ScheduledPetEntity {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private ScheduleEntity scheduleEntity;
  
  @ManyToOne
  private PetEntity petEntity;
  
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
  
  public PetEntity getPetEntity() {
    return petEntity;
  }
  
  public void setPetEntity(PetEntity petEntity) {
    this.petEntity = petEntity;
  }
}

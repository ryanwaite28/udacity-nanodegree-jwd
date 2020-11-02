package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.*;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
  
  @Autowired
  private ScheduleRepo scheduleRepo;
  
  @Autowired
  private ScheduledActivityRepo scheduledActivityRepo;
  
  @Autowired
  private ScheduledEmployeeRepo scheduledEmployeeRepo;
  
  @Autowired
  private ScheduledPetRepo scheduledPetRepo;
  
  @Autowired
  private EmployeeRepo employeeRepo;
  
  @Autowired
  private PetRepo petRepo;
  
  public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
    // create the new schedule
    ScheduleEntity scheduleEntity = new ScheduleEntity();
    scheduleEntity.setDate(scheduleDTO.getDate());
    scheduleRepo.save(scheduleEntity);
    
    // loop through the pets, employees, and activities
    for (Long employeeId : scheduleDTO.getEmployeeIds()) {
      ScheduledEmployeeEntity scheduledEmployeeEntity = new ScheduledEmployeeEntity();
      Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(employeeId);
      if (!employeeEntity.isPresent()) {
        continue;
      }
      scheduledEmployeeEntity.setEmployeeEntity(employeeEntity.get());
      scheduledEmployeeEntity.setScheduleEntity(scheduleEntity);
      scheduleEntity.getScheduledEmployeeEntityList().add(scheduledEmployeeEntity);
    }
    for (Long petId : scheduleDTO.getPetIds()) {
      ScheduledPetEntity scheduledPetEntity = new ScheduledPetEntity();
      Optional<PetEntity> petEntity = petRepo.findById(petId);
      if (!petEntity.isPresent()) {
        continue;
      }
      scheduledPetEntity.setPetEntity(petEntity.get());
      scheduledPetEntity.setScheduleEntity(scheduleEntity);
      scheduleEntity.getScheduledPetEntityList().add(scheduledPetEntity);
    }
    for (EmployeeSkill skill : scheduleDTO.getActivities()) {
      ScheduledActivityEntity scheduledActivityEntity = new ScheduledActivityEntity();
      scheduledActivityEntity.setEmployeeSkill(skill);
      scheduledActivityEntity.setScheduleEntity(scheduleEntity);
      scheduleEntity.getScheduledActivityEntityList().add(scheduledActivityEntity);
    }
    
    scheduleDTO.setId(scheduleEntity.getId());
    return scheduleDTO;
  }
  
  public List<ScheduleDTO> getAllSchedules() {
    List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
    List<ScheduleEntity> scheduleEntityList = scheduleRepo.findAll();
    for (ScheduleEntity scheduleEntity : scheduleEntityList) {
      ScheduleDTO scheduleDTO = new ScheduleDTO();
      scheduleDTO.setId(scheduleEntity.getId());
      scheduleDTO.setDate(scheduleEntity.getDate());
      for (ScheduledActivityEntity scheduledActivityEntity : scheduleEntity.getScheduledActivityEntityList()) {
        scheduleDTO.getActivities().add(scheduledActivityEntity.getEmployeeSkill());
      }
      for (ScheduledEmployeeEntity scheduledEmployeeEntity : scheduleEntity.getScheduledEmployeeEntityList()) {
        scheduleDTO.getEmployeeIds().add(scheduledEmployeeEntity.getEmployeeEntity().getId());
      }
      for (ScheduledPetEntity scheduledPetEntity : scheduleEntity.getScheduledPetEntityList()) {
        scheduleDTO.getPetIds().add(scheduledPetEntity.getPetEntity().getId());
      }
      scheduleDTOList.add(scheduleDTO);
    }
    return scheduleDTOList;
  }
  
  public List<ScheduleDTO> getScheduleForPet(long petId) {
    List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
    List<ScheduleEntity> scheduleEntityList = scheduleRepo.findAllByScheduledPetEntityListPetEntityId(petId); // scheduledPetRepo.findAllScheduleEntityByPetEntityId(petId);
    for (ScheduleEntity scheduleEntity : scheduleEntityList) {
      ScheduleDTO scheduleDTO = new ScheduleDTO();
      scheduleDTO.setId(scheduleEntity.getId());
      scheduleDTO.setDate(scheduleEntity.getDate());
      for (ScheduledActivityEntity scheduledActivityEntity : scheduleEntity.getScheduledActivityEntityList()) {
        scheduleDTO.getActivities().add(scheduledActivityEntity.getEmployeeSkill());
      }
      for (ScheduledEmployeeEntity scheduledEmployeeEntity : scheduleEntity.getScheduledEmployeeEntityList()) {
        scheduleDTO.getEmployeeIds().add(scheduledEmployeeEntity.getEmployeeEntity().getId());
      }
      for (ScheduledPetEntity scheduledPetEntity : scheduleEntity.getScheduledPetEntityList()) {
        scheduleDTO.getPetIds().add(scheduledPetEntity.getPetEntity().getId());
      }
      scheduleDTOList.add(scheduleDTO);
    }
    return scheduleDTOList;
  }
  
  public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
    List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
    List<ScheduleEntity> scheduleEntityList = scheduleRepo.findAllByScheduledEmployeeEntityListEmployeeEntityId(employeeId); // scheduledEmployeeRepo.findAllScheduleEntityByEmployeeEntityId(employeeId);
    for (ScheduleEntity scheduleEntity : scheduleEntityList) {
      ScheduleDTO scheduleDTO = new ScheduleDTO();
      scheduleDTO.setId(scheduleEntity.getId());
      scheduleDTO.setDate(scheduleEntity.getDate());
      for (ScheduledActivityEntity scheduledActivityEntity : scheduleEntity.getScheduledActivityEntityList()) {
        scheduleDTO.getActivities().add(scheduledActivityEntity.getEmployeeSkill());
      }
      for (ScheduledEmployeeEntity scheduledEmployeeEntity : scheduleEntity.getScheduledEmployeeEntityList()) {
        scheduleDTO.getEmployeeIds().add(scheduledEmployeeEntity.getEmployeeEntity().getId());
      }
      for (ScheduledPetEntity scheduledPetEntity : scheduleEntity.getScheduledPetEntityList()) {
        scheduleDTO.getPetIds().add(scheduledPetEntity.getPetEntity().getId());
      }
      scheduleDTOList.add(scheduleDTO);
    }
    return scheduleDTOList;
  }
  
  public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
    List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
    List<ScheduleEntity> scheduleEntityList = scheduleRepo.findAllByScheduledPetEntityListPetEntityCustomerEntityId(customerId); // scheduledPetRepo.findAllScheduleEntityByPetEntityCustomerEntityId(customerId);
    for (ScheduleEntity scheduleEntity : scheduleEntityList) {
      ScheduleDTO scheduleDTO = new ScheduleDTO();
      scheduleDTO.setId(scheduleEntity.getId());
      scheduleDTO.setDate(scheduleEntity.getDate());
      for (ScheduledActivityEntity scheduledActivityEntity : scheduleEntity.getScheduledActivityEntityList()) {
        scheduleDTO.getActivities().add(scheduledActivityEntity.getEmployeeSkill());
      }
      for (ScheduledEmployeeEntity scheduledEmployeeEntity : scheduleEntity.getScheduledEmployeeEntityList()) {
        scheduleDTO.getEmployeeIds().add(scheduledEmployeeEntity.getEmployeeEntity().getId());
      }
      for (ScheduledPetEntity scheduledPetEntity : scheduleEntity.getScheduledPetEntityList()) {
        scheduleDTO.getPetIds().add(scheduledPetEntity.getPetEntity().getId());
      }
      scheduleDTOList.add(scheduleDTO);
    }
    return scheduleDTOList;
  }
  
}

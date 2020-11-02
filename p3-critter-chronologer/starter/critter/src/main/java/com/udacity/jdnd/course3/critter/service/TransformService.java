package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.CustomerRepo;
import com.udacity.jdnd.course3.critter.repository.EmployeeAvailabilityRepo;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepo;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TransformService {
  
  @Autowired
  private CustomerRepo customerRepo;
  
  @Autowired
  private EmployeeRepo employeeRepo;
  
  @Autowired
  private EmployeeSkillRepo employeeSkillRepo;
  
  @Autowired
  private EmployeeAvailabilityRepo employeeAvailabilityRepo;
  
  public CustomerDTO convertCustomerEntityToDto(CustomerEntity customerEntity) {
    CustomerDTO customerDTO = new CustomerDTO();
    
    customerDTO.setId(customerEntity.getId());
    customerDTO.setName(customerEntity.getName());
    customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
    customerDTO.setNotes(customerEntity.getNotes());
    List<PetEntity> petEntityList = customerEntity.getPets();
    if (petEntityList.size() > 0) {
      customerDTO.setPetIds(new ArrayList<>());
      for (PetEntity petEntity : petEntityList) {
        customerDTO.getPetIds().add(petEntity.getId());
      }
    }
    return customerDTO;
  }
  
  /*public CustomerEntity convertCustomerDtoToEntity(CustomerDTO customerDTO) {
    CustomerEntity customerEntity = new CustomerEntity();
  
    customerEntity.setId(customerDTO.getId());
    customerEntity.setName(customerDTO.getName());
    customerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
    customerEntity.setNotes(customerDTO.getNotes());
    customerEntity.setPetIds(customerDTO.getPetIds());
    
    return customerEntity;
  }*/
  
  public EmployeeDTO convertEmployeeEntityToDto(EmployeeEntity employeeEntity) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    
    employeeDTO.setId(employeeEntity.getId());
    employeeDTO.setName(employeeEntity.getName());
    if (employeeEntity.getEmployeeAvailableEntityList() != null && employeeEntity.getEmployeeAvailableEntityList().size() > 0) {
      List<DayOfWeek> dayOfWeekList = new ArrayList<>();
      for (EmployeeAvailableEntity employeeAvailableEntity : employeeEntity.getEmployeeAvailableEntityList()) {
        dayOfWeekList.add(employeeAvailableEntity.getDayAvailable());
      }
      employeeDTO.setDaysAvailable(new HashSet<>(dayOfWeekList));
    }
    if (employeeEntity.getSkillEntityList() != null && employeeEntity.getSkillEntityList().size() > 0) {
      List<EmployeeSkill> employeeSkillArrayList = new ArrayList<>();
      for (EmployeeSkillEntity employeeSkillEntity : employeeEntity.getSkillEntityList()) {
        employeeSkillArrayList.add(employeeSkillEntity.getSkill());
      }
      employeeDTO.setSkills(new HashSet<>(employeeSkillArrayList));
    }
    
    return employeeDTO;
  }
  
  /*public EmployeeEntity convertEmployeeDtoToEntity(EmployeeDTO employeeDTO) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
  
    employeeEntity.setId(employeeDTO.getId());
    employeeEntity.setName(employeeDTO.getName());
    if (employeeDTO.getDaysAvailable() != null) {
      employeeEntity.setDaysAvailable(new ArrayList<DayOfWeek>(employeeDTO.getDaysAvailable()));
    }
    if (employeeDTO.getSkills() != null) {
      employeeEntity.setSkills(new ArrayList<EmployeeSkill>(employeeDTO.getSkills()));
    }
    
    return employeeEntity;
  }*/
}

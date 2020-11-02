package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.EmployeeAvailableEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkillEntity;
import com.udacity.jdnd.course3.critter.repository.EmployeeAvailabilityRepo;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepo;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepo;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
  
  @Autowired
  private EmployeeRepo employeeRepo;
  
  @Autowired
  private EmployeeSkillRepo employeeSkillRepo;
  
  @Autowired
  private EmployeeAvailabilityRepo employeeAvailabilityRepo;
  
  @Autowired
  private TransformService transformService;
  
  public List<EmployeeDTO> findEmployeesForService (EmployeeRequestDTO employeeRequestDTO) {
    // first, find employees that have the skill desired by request
    List<EmployeeEntity> employeeSkillEntityList = employeeRepo.findAllBySkillEntityListSkillIn(employeeRequestDTO.getSkills());
    
    // prepare list of employees that match the requested skills and date
    List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
    
    // get day of week from requested date
    LocalDate requestDate = employeeRequestDTO.getDate();
    DayOfWeek requestedDateWeekDay = requestDate.getDayOfWeek();
    
    // loop through each employee and check their availability
    for (EmployeeEntity employeeEntity : employeeSkillEntityList) {
//      EmployeeEntity employeeEntity = employeeSkillEntity.getEmployeeEntity();
      // if they are not available, move to next iteration
      List<DayOfWeek> employeeAvailability = employeeEntity.getEmployeeAvailableEntityList().stream().map(EmployeeAvailableEntity::getDayAvailable).collect(Collectors.toList());
      if (!employeeAvailability.contains(requestedDateWeekDay)) {
        continue;
      }
      
      List<EmployeeSkill> employeeSkillList = employeeEntity.getSkillEntityList().stream().map(EmployeeSkillEntity::getSkill).collect(Collectors.toList());
      // if the requested set is not a subset of the employee's skills, move to next iteration
      boolean isSubSet = checkSubSet(employeeSkillList, new ArrayList<>(employeeRequestDTO.getSkills()));
      if (!isSubSet) {
        continue;
      }
      // everything is okay, add to list
      EmployeeDTO employeeDTO = transformService.convertEmployeeEntityToDto(employeeEntity);
      employeeDTOList.add(employeeDTO);
    }
    
    return employeeDTOList;
  }
  
  private <T> boolean checkSubSet(List<T> mainSet, List<T> subSet) {
    for (T item : subSet) {
      if (!mainSet.contains(item)) {
        return false;
      }
    }
    return true;
  }
  
  public EmployeeDTO getEmployee(long employeeId) {
    Optional<EmployeeEntity> employeeEntityQuery = employeeRepo.findById(employeeId);
    if (!employeeEntityQuery.isPresent()) {
      return null;
    }
    EmployeeEntity employeeEntity = employeeEntityQuery.get();
    employeeEntity.getSkillEntityList();
    employeeEntity.getEmployeeAvailableEntityList();
    EmployeeDTO employeeDTO = transformService.convertEmployeeEntityToDto(employeeEntity);
    return employeeDTO;
  }
  
  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    EmployeeEntity newEmployeeEntity = new EmployeeEntity();
    newEmployeeEntity.setId(employeeDTO.getId());
    newEmployeeEntity.setName(employeeDTO.getName());
    employeeRepo.save(newEmployeeEntity);
    if (employeeDTO.getSkills() != null) {
      for (EmployeeSkill employeeSkill : employeeDTO.getSkills()) {
        EmployeeSkillEntity employeeSkillEntity = new EmployeeSkillEntity();
        employeeSkillEntity.setSkill(employeeSkill);
        employeeSkillEntity.setEmployeeEntity(newEmployeeEntity);
        newEmployeeEntity.getSkillEntityList().add(employeeSkillEntity);
      }
    }
    if (employeeDTO.getDaysAvailable() != null) {
      for (DayOfWeek dayOfWeek : employeeDTO.getDaysAvailable()) {
        EmployeeAvailableEntity employeeAvailableEntity = new EmployeeAvailableEntity();
        employeeAvailableEntity.setDayAvailable(dayOfWeek);
        employeeAvailableEntity.setEmployeeEntity(newEmployeeEntity);
        newEmployeeEntity.getEmployeeAvailableEntityList().add(employeeAvailableEntity);
      }
    }
    employeeDTO.setId(newEmployeeEntity.getId());
    return employeeDTO;
  }
  
  public boolean setAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
    // check if employee exists
    Optional<EmployeeEntity> employeeEntityQuery = employeeRepo.findById(employeeId);
    if (!employeeEntityQuery.isPresent()) {
      return false;
    }
    EmployeeEntity employeeEntity = employeeEntityQuery.get();
    // delete their current availabilities
    employeeAvailabilityRepo.deleteByEmployeeEntityId(employeeId);
    // for each daysAvailable from request, create new availability for employee
    for (DayOfWeek dayOfWeek : daysAvailable) {
      EmployeeAvailableEntity employeeAvailableEntity = new EmployeeAvailableEntity();
      employeeAvailableEntity.setDayAvailable(dayOfWeek);
      employeeAvailableEntity.setEmployeeEntity(employeeEntity);
      employeeEntity.getEmployeeAvailableEntityList().add(employeeAvailableEntity);
    }
    // no error thus far; return true
    return true;
  }
}

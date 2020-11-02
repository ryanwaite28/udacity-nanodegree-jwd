package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
  
  @Autowired
  private ScheduleService scheduleService;

  @PostMapping
  public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    ScheduleDTO newScheduleDTO = scheduleService.createSchedule(scheduleDTO);
    return newScheduleDTO;
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    List<ScheduleDTO> scheduleDTOList = scheduleService.getAllSchedules();
    return scheduleDTOList;
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForPet(petId);
    return scheduleDTOList;
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForEmployee(employeeId);
    return scheduleDTOList;
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForCustomer(customerId);
    return scheduleDTOList;
  }
}

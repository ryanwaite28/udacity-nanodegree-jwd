package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
  
  @Autowired
  private CustomerService customerService;
  
  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private PetService petService;
  
  /* GET */
  
  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
    return customerDTOList;
  }
  
  @GetMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(
    @PathVariable long employeeId
  ) {
    EmployeeDTO employeeDTO = employeeService.getEmployee(employeeId);
    return employeeDTO;
  }
  
  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(
    @PathVariable long petId
  ) {
    CustomerDTO customerDTO = petService.getOwnerByPet(petId);
    return customerDTO;
  }
  
  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(
    @RequestBody EmployeeRequestDTO employeeRequestDTO
  ) {
    List<EmployeeDTO> employeeDTOList = employeeService.findEmployeesForService(employeeRequestDTO);
    return employeeDTOList;
  }
  
  /* POST */
  
  @PostMapping("/customer")
  public CustomerDTO saveCustomer(
    @RequestBody CustomerDTO customerDTO
  ) {
    CustomerDTO newCustomer = customerService.saveCustomer(customerDTO);
    return  newCustomer;
  }
  
  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(
    @RequestBody EmployeeDTO employeeDTO
  ) {
    EmployeeDTO newEmployeeDTO = employeeService.saveEmployee(employeeDTO);
    return newEmployeeDTO;
  }
  
  /* PUT */
  
  @PutMapping("/employee/{employeeId}")
  public JSONObject setAvailability(
    @RequestBody Set<DayOfWeek> daysAvailable,
    @PathVariable long employeeId
  ) {
    boolean wasSuccessful = employeeService.setAvailability(employeeId, daysAvailable);
    JSONObject responseJson = new JSONObject();
    responseJson.put("successful", wasSuccessful);
    return responseJson;
  }
  
  /* DELETE */

}

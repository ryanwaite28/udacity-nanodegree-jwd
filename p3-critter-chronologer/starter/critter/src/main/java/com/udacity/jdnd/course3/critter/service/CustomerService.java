package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
  
  @Autowired
  private CustomerRepo customerRepo;
  
  @Autowired
  private TransformService transformService;
  
  public List<CustomerDTO> getAllCustomers() {
    List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
    List<CustomerEntity> customerEntityList = customerRepo.findAll();
    for (CustomerEntity customerEntity : customerEntityList) {
      CustomerDTO customerDTO = transformService.convertCustomerEntityToDto(customerEntity);
      customerDTOList.add(customerDTO);
    }
    return customerDTOList;
  }
  
  public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    CustomerEntity newCustomerEntity = new CustomerEntity();
    newCustomerEntity.setId(customerDTO.getId());
    newCustomerEntity.setName(customerDTO.getName());
    newCustomerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
    newCustomerEntity.setNotes(customerDTO.getNotes());
    customerRepo.save(newCustomerEntity);
    CustomerDTO newCustomerDTO = transformService.convertCustomerEntityToDto(newCustomerEntity);
    return newCustomerDTO;
  }
}

package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepo;
import com.udacity.jdnd.course3.critter.repository.PetRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
  
  @Autowired
  private PetRepo petRepo;
  
  @Autowired
  private CustomerRepo customerRepo;
  
  @Autowired
  private TransformService transformService;
  
  public PetDTO getPet(long petId) {
    Optional<PetEntity> petEntityQuery = petRepo.findById(petId);
    if (!petEntityQuery.isPresent()) {
      return null;
    }
    PetEntity petEntity = petEntityQuery.get();
    PetDTO petDTO = new PetDTO();
    petDTO.setId(petEntity.getId());
    petDTO.setOwnerId(petEntity.getCustomerEntity() == null ? null : petEntity.getCustomerEntity().getId());
    petDTO.setName(petEntity.getName());
    petDTO.setType(petEntity.getType());
    petDTO.setBirthDate(petEntity.getBirthDate());
    petDTO.setNotes(petEntity.getNotes());
    return petDTO;
  }
  
  public List<PetDTO> getPets() {
    List<PetDTO> petDTOList = new ArrayList<PetDTO>();
    List<PetEntity> petEntityList = petRepo.findAll();
    for (PetEntity petEntity : petEntityList) {
      PetDTO petDTO = new PetDTO();
      petDTO.setId(petEntity.getId());
      petDTO.setOwnerId(petEntity.getCustomerEntity() == null ? null : petEntity.getCustomerEntity().getId());
      petDTO.setName(petEntity.getName());
      petDTO.setType(petEntity.getType());
      petDTO.setBirthDate(petEntity.getBirthDate());
      petDTO.setNotes(petEntity.getNotes());
      petDTOList.add(petDTO);
    }
    return petDTOList;
  }
  
  public CustomerDTO getOwnerByPet(long petId) {
    Optional<PetEntity> petEntityQuery = petRepo.findById(petId);
    if (!petEntityQuery.isPresent()) {
      return null;
    }
    Optional<CustomerEntity> customerEntityQuery = customerRepo.findById(petEntityQuery.get().getCustomerEntity().getId());
    if (!customerEntityQuery.isPresent()) {
      return null;
    }
    CustomerEntity customerEntity = customerEntityQuery.get();
    CustomerDTO customerDTO = new CustomerDTO();
  
    customerDTO.setId(customerEntity.getId());
    customerDTO.setName(customerEntity.getName());
    customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
    customerDTO.setNotes(customerEntity.getNotes());
    List<Long> petIds = new ArrayList<>();
    for (PetEntity petEntity : customerEntity.getPets()) {
      petIds.add(petEntity.getId());
    }
    customerDTO.setPetIds(petIds);
    
    return customerDTO;
  }
  
  public List<PetDTO> getPetsByOwner(long ownerId) {
    List<PetDTO> petDTOList = new ArrayList<PetDTO>();
    List<PetEntity> petEntityList = petRepo.findAllByCustomerEntityId(ownerId);
    for (PetEntity petEntity : petEntityList) {
      PetDTO petDTO = new PetDTO();
      petDTO.setId(petEntity.getId());
      petDTO.setOwnerId(petEntity.getCustomerEntity() == null ? null : petEntity.getCustomerEntity().getId());
      petDTO.setName(petEntity.getName());
      petDTO.setType(petEntity.getType());
      petDTO.setBirthDate(petEntity.getBirthDate());
      petDTO.setNotes(petEntity.getNotes());
      petDTOList.add(petDTO);
    }
    return petDTOList;
  }
  
  public PetDTO savePet(PetDTO petDTO) {
    PetEntity petEntity = new PetEntity();
    petEntity.setName(petDTO.getName());
    petEntity.setNotes(petDTO.getNotes());
    petEntity.setBirthDate(petDTO.getBirthDate());
    petEntity.setType(petDTO.getType());
    Optional<CustomerEntity> customerEntity = customerRepo.findById(petDTO.getOwnerId());
    if (customerEntity.isPresent()) {
      petEntity.setCustomerEntity(customerEntity.get());
      customerEntity.get().getPets().add(petEntity);
    }
    petRepo.save(petEntity);
    petDTO.setId(petEntity.getId());
    return petDTO;
  }
}

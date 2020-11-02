package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
  @Autowired
  private PetService petService;

  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
    PetDTO newPetDTO = petService.savePet(petDTO);
    return newPetDTO;
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    PetDTO newPetDTO = petService.getPet(petId);
    return newPetDTO;
  }

  @GetMapping
  public List<PetDTO> getPets() {
    List<PetDTO> petDTOList = petService.getPets();
    return petDTOList;
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<PetDTO> petDTOList = petService.getPetsByOwner(ownerId);
    return petDTOList;
  }
}

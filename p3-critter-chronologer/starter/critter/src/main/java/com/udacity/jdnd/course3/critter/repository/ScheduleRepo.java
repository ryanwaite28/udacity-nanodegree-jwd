package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<ScheduleEntity, Long> {
  List<ScheduleEntity> findAllByScheduledEmployeeEntityListEmployeeEntityId(Long id);
  List<ScheduleEntity> findAllByScheduledPetEntityListPetEntityId(Long id);
  List<ScheduleEntity> findAllByScheduledPetEntityListPetEntityCustomerEntityId(Long id);
}

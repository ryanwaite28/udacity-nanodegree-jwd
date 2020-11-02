package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduledPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledPetRepo extends JpaRepository<ScheduledPetEntity, Long> {
  List<ScheduleEntity> findAllScheduleEntityByPetEntityId(Long id);
  List<ScheduleEntity> findAllScheduleEntityByPetEntityCustomerEntityId(Long id);
}

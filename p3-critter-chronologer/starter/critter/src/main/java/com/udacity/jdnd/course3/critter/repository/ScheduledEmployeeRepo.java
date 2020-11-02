package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduledEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledEmployeeRepo extends JpaRepository<ScheduledEmployeeEntity, Long> {
  List<ScheduleEntity> findAllScheduleEntityByEmployeeEntityId(Long id);
}

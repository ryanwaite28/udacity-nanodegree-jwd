package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeAvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAvailabilityRepo extends JpaRepository<EmployeeAvailableEntity, Long> {
  List<EmployeeAvailableEntity> findAllByEmployeeEntityId(Long employeeId);
  long deleteByEmployeeEntityId(Long employeeId);
}

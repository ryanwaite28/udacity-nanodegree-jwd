package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {
  List<EmployeeEntity> findAllByIdIn(List<Long> ids);
  List<EmployeeEntity> findAllBySkillEntityListSkillIn(Set<EmployeeSkill> employeeSkills);
}

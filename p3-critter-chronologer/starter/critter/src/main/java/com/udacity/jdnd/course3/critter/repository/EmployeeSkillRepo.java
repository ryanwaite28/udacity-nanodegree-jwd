package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkillEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeSkillRepo extends JpaRepository<EmployeeSkillEntity, Long> {
  List<EmployeeSkillEntity> findAllByEmployeeEntityId(long employeeId);
  List<EmployeeEntity> findAllEmployeeBySkill(EmployeeSkill employeeSkill);
  List<EmployeeEntity> findAllEmployeeEntityBySkillIn(Set<EmployeeSkill> employeeSkills);
  List<EmployeeSkillEntity> findAllBySkillIn(Set<EmployeeSkill> employeeSkills);
  List<EmployeeEntity> findDistinctEmployeeEntityBySkillIn(Set<EmployeeSkill> employeeSkills);
}

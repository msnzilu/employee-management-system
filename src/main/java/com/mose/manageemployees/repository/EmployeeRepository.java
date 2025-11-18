package com.mose.manageemployees.repository;

import com.mose.manageemployees.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Empty for now - we get save, findAll, findById, etc. automatically!
}
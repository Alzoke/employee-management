package com.barkan.employeemanagement.repositories;

import com.barkan.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByEmail(String email);
}

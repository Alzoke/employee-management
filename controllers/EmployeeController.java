package com.barkan.employeemanagement.controllers;

import com.barkan.employeemanagement.models.Employee;
import com.barkan.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Integer id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeByEmail(@RequestParam(name = "email") String requestEmail){
        return employeeService.getEmployeeByEmail(requestEmail);
    }

    @PostMapping("/employees/")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }
}

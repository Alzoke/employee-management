package com.barkan.employeemanagement.services;

import com.barkan.employeemanagement.assemblers.EmployeeAssembler;
import com.barkan.employeemanagement.exceptions.EmployeeEmailNotFoundException;
import com.barkan.employeemanagement.exceptions.EmployeeEmailAlreadyExistsException;
import com.barkan.employeemanagement.exceptions.EmployeeNotFoundException;
import com.barkan.employeemanagement.models.Employee;
import com.barkan.employeemanagement.repositories.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private EmployeeAssembler employeeAssembler;

    public ResponseEntity<?> updateEmployee(Integer id, Employee employee){
        Employee oldEmployeeEntity = employeeRepository.findById(id).orElse(employee);

        //New employee was created because there was no employee with the corresponding id in the DB.
        if (oldEmployeeEntity.equals(employee)){
            EntityModel<Employee> newEmployee = employeeAssembler.toModel(employeeRepository.save(employee));

            try {
                return ResponseEntity.created(new URI(
                        newEmployee.getRequiredLink(IanaLinkRelations.SELF).getHref())).body(newEmployee);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        //Employee with corresponding id already exists, so we have to update it.
        else {
            //TODO: Update fields with reflection
            oldEmployeeEntity.setFirstName(employee.getFirstName());
            oldEmployeeEntity.setLastName(employee.getLastName());
            oldEmployeeEntity.setEmail(employee.getEmail());
            employeeRepository.save(oldEmployeeEntity);
            return ResponseEntity.ok(employeeAssembler.toModel(oldEmployeeEntity));
        }

        return ResponseEntity.internalServerError().body("Error while updating employee");
    }

    public ResponseEntity<CollectionModel<EntityModel<Employee>>> getAllEmployees(){
        return ResponseEntity.ok(employeeAssembler.toCollectionModel(employeeRepository.findAll()));
    }

    public ResponseEntity<?> getEmployee(Integer id){
        return employeeRepository.findById(id)
                .map(employeeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public ResponseEntity<?> createEmployee(Employee employee){
        if (employeeRepository.findEmployeeByEmail(employee.getEmail()).isPresent()){
            throw  new EmployeeEmailAlreadyExistsException(employee.getEmail());
        }

        try {
            EntityModel<Employee> newEmployee = employeeAssembler.toModel(employeeRepository.save(employee));
            return ResponseEntity.created(new URI(
                    newEmployee.getRequiredLink(IanaLinkRelations.SELF).getHref())).body(newEmployee);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().body("Error while creating new employee");
    }

    public ResponseEntity<?> deleteEmployee(Integer id){
        EntityModel<Employee> employeeEntityModel = employeeRepository.findById(id)
                .map(employeeAssembler::toModel).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.deleteById(id);

        return ResponseEntity.ok(employeeEntityModel);
    }


    public ResponseEntity<?> getEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email)
                .map(employeeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EmployeeEmailNotFoundException(email));
    }
}

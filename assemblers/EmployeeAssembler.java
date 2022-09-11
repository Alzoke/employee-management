package com.barkan.employeemanagement.assemblers;

import com.barkan.employeemanagement.controllers.EmployeeController;
import com.barkan.employeemanagement.models.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public CollectionModel<EntityModel<Employee>> toCollectionModel(Iterable<? extends Employee> entities) {
        List<Employee> employees = (List<Employee>) entities;
        List<EntityModel<Employee>> entityModels = employees.stream().map(this::toModel).collect(Collectors.toList());
        Link link = linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel();

        return CollectionModel.of(entityModels, link);
    }

    @Override
    public EntityModel<Employee> toModel(Employee entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EmployeeController.class).getEmployee(entity.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("All employees"));
    }
}

package com.barkan.employeemanagement.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Integer id){
        super("There is no Employee corresponding to the following ID : " + id);
    }
}

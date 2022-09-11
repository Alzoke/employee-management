package com.barkan.employeemanagement.exceptions;

public class EmployeeEmailNotFoundException extends RuntimeException{
    public EmployeeEmailNotFoundException(String email) {
        super("There is no Employee corresponding to the following email : " + email);
    }
}

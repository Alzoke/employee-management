package com.barkan.employeemanagement.exceptions;

public class EmployeeEmailAlreadyExistsException extends RuntimeException{
    public EmployeeEmailAlreadyExistsException(String email) {
        super("The corresponding email : " + email + " already exists in the DB (email is a unique field)");
    }
}

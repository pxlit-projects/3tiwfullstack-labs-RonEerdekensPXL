package be.pxl.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee not found")
public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() { }
    public EmployeeNotFoundException(String message) { super(message); }
    public EmployeeNotFoundException(String message, Throwable cause) { super(message, cause); }
}

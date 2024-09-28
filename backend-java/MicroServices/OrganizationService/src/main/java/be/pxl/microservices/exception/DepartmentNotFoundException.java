package be.pxl.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Department not found")
public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(){}
    public DepartmentNotFoundException(String message){super(message);}
    public DepartmentNotFoundException(String message, Throwable cause){super(message, cause);}
}

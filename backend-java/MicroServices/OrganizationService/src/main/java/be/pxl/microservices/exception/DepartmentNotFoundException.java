package be.pxl.microservices.exception;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(){}
    public DepartmentNotFoundException(String message){super(message);}
    public DepartmentNotFoundException(String message, Throwable cause){super(message, cause);}
}

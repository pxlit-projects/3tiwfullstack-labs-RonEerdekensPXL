package be.pxl.microservices.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Organization not found")
public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException() {}
    public OrganizationNotFoundException(String message) {super(message);}
    public OrganizationNotFoundException(String message, Throwable cause) {super(message, cause);}
}

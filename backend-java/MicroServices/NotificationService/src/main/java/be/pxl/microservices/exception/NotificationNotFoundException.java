package be.pxl.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Notification not found")
public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException() {}
    public NotificationNotFoundException(String message) {super(message);}
    public NotificationNotFoundException(String message, Throwable cause) {super(message, cause);}
}

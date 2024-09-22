package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private long id;
    private String from;
    private String to;
    private String subject;
    private String message;


    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.from = notification.getFrom();
        this.to = notification.getTo();
        this.subject = notification.getSubject();
        this.message = notification.getMessage();
    }


}

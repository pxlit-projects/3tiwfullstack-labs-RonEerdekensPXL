package be.pxl.microservices.api.dto.response;

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

}

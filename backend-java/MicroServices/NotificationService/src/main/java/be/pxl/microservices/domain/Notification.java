package be.pxl.microservices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private long id;
    private String from;
    private String to;
    private String subject;
    private Message message;
}

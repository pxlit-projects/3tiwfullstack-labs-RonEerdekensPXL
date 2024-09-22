package be.pxl.microservices.domain;

import be.pxl.microservices.api.request.NotificationRequest;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.message.Message;

@Entity
@Table(name = "notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;
    private String subject;

    private String message;

    public Notification(NotificationRequest notificationRequest) {
        this.from = notificationRequest.getFrom();
        this.to = notificationRequest.getTo();
        this.subject = notificationRequest.getSubject();
        this.message = notificationRequest.getMessage();
    }
}

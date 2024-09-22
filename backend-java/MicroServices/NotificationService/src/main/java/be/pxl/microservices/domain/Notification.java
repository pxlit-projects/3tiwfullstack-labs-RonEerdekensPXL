package be.pxl.microservices.domain;

import jakarta.persistence.*;
import lombok.*;

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

}

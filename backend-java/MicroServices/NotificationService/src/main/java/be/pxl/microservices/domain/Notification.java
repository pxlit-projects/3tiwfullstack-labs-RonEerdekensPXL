package be.pxl.microservices.domain;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {


    private String from;

    private String to;
    private String subject;

    private String message;

}

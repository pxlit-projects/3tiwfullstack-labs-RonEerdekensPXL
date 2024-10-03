package be.pxl.microservices.services;

import be.pxl.microservices.domain.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServices implements INotificationServices {




    @Override
    public void sendMessage(Notification notification) {
        log.info("Receiving notification....");
        log.info("Sending ... {}", notification.getMessage());
        log.info("To ... {}", notification.getTo());
    }
}

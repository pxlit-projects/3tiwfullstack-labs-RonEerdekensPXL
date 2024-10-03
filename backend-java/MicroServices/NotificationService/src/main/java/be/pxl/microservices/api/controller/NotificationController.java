package be.pxl.microservices.api.controller;


import be.pxl.microservices.domain.Notification;

import be.pxl.microservices.services.INotificationServices;
import be.pxl.microservices.services.NotificationServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final INotificationServices notificationServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody Notification notification) {
        notificationServices.sendMessage(notification);
    }
}

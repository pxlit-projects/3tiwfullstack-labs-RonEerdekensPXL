package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.NotificationRequest;
import be.pxl.microservices.api.dto.response.NotificationResponse;
import be.pxl.microservices.exception.NotificationNotFoundException;
import be.pxl.microservices.services.INotificationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationServices notificationServices;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        return new ResponseEntity<>(notificationServices.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {

        NotificationResponse notificationResponse = notificationServices.getNotificationById(id);
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest notificationRequest) {
        return new ResponseEntity<>(notificationServices.createNotification(notificationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable Long id, @RequestBody NotificationRequest notificationRequest) {

        NotificationResponse notificationResponse = notificationServices.updateNotification(id, notificationRequest);
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNotification(@PathVariable Long id) {

        notificationServices.deleteNotification(id);
        return new ResponseEntity(HttpStatus.OK);

    }
}

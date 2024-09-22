package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.NotificationRequest;
import be.pxl.microservices.api.dto.response.NotificationResponse;

import java.util.List;

public interface INotificationServices {
    List<NotificationResponse> getAllDepartments();

    NotificationResponse getNotificationById(Long id);

    NotificationResponse createNotification(NotificationRequest notificationRequest);

    NotificationResponse updateNotification(Long id, NotificationRequest notificationRequest);

    void deleteNotification(Long id);
}

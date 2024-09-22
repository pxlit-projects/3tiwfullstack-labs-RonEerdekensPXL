package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.NotificationRequest;
import be.pxl.microservices.api.dto.response.NotificationResponse;
import be.pxl.microservices.domain.Notification;
import be.pxl.microservices.exception.NotificationNotFoundException;
import be.pxl.microservices.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServices implements INotificationServices {

    private final NotificationRepository notificationRepository;

    private NotificationResponse mapToNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .from(notification.getFrom())
                .to(notification.getTo())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .build();
    }

    @Override
    public List<NotificationResponse> getAllDepartments() {
        return notificationRepository.findAll().stream().map(this::mapToNotificationResponse).toList();
    }

    @Override
    public NotificationResponse getNotificationById(Long id) {
        return notificationRepository.findById(id).map(this::mapToNotificationResponse).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
    }

    @Override
    public NotificationResponse createNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .from(notificationRequest.getFrom())
                .to(notificationRequest.getTo())
                .subject(notificationRequest.getSubject())
                .message(notificationRequest.getMessage())
                .build();
        notification = notificationRepository.save(notification);
        return mapToNotificationResponse(notification);
    }

    @Override
    public NotificationResponse updateNotification(Long id, NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
        notification.setFrom(notificationRequest.getFrom());
        notification.setTo(notificationRequest.getTo());
        notification.setSubject(notificationRequest.getSubject());
        notification.setMessage(notificationRequest.getMessage());
        notificationRepository.save(notification);
        return mapToNotificationResponse(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
        notificationRepository.deleteById(id);
    }
}

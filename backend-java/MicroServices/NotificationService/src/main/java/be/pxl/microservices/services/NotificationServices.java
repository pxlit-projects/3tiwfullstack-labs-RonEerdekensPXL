package be.pxl.microservices.services;

import be.pxl.microservices.api.request.NotificationRequest;
import be.pxl.microservices.api.response.NotificationResponse;
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

    @Override
    public List<NotificationResponse> getAllDepartments() {
        return notificationRepository.findAll().stream().map(NotificationResponse::new).toList();
    }

    @Override
    public NotificationResponse getNotificationById(Long id) {
        return notificationRepository.findById(id).map(NotificationResponse::new).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
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
        return new NotificationResponse(notification);
    }

    @Override
    public NotificationResponse updateNotification(Long id, NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
        notification.setFrom(notificationRequest.getFrom());
        notification.setTo(notificationRequest.getTo());
        notification.setSubject(notificationRequest.getSubject());
        notification.setMessage(notificationRequest.getMessage());
        notificationRepository.save(notification);
        return new NotificationResponse(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Notification with " + id + " not found"));
        notificationRepository.deleteById(id);
    }
}

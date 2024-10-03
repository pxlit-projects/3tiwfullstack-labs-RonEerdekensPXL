package be.pxl.microservices.services;


import be.pxl.microservices.domain.Notification;

import java.util.List;

public interface INotificationServices {

    void sendMessage(Notification notification);
}

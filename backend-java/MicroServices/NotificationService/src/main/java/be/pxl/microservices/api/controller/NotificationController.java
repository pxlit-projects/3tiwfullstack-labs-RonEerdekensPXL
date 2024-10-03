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


}

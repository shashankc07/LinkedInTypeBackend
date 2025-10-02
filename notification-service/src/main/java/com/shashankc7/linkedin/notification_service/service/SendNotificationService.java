package com.shashankc7.linkedin.notification_service.service;

import com.shashankc7.linkedin.notification_service.entity.Notification;
import com.shashankc7.linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotificationService
{
    private final NotificationRepository notificationRepository;

    public void send(Long userId, String message)
    {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);

        notificationRepository.save(notification);
    }
}

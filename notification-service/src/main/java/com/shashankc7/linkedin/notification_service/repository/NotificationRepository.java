package com.shashankc7.linkedin.notification_service.repository;

import com.shashankc7.linkedin.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>
{
}

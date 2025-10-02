package com.shashankc7.linkedin.notification_service.consumer;

import com.shashankc7.linkedin.connection_service.event.AcceptConnectionRequestEvent;
import com.shashankc7.linkedin.connection_service.event.SendConnectionRequestEvent;
import com.shashankc7.linkedin.notification_service.service.SendNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceConsumer
{
    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent)
    {
        String message = "You have received a connection request from user with id: {}" + sendConnectionRequestEvent.getSenderId();
        log.info(message);
        sendNotificationService.send(sendConnectionRequestEvent.getReceiverId(), message);
    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void hadnleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent)
    {
        String message = "Your request has been accepted by the user with id: {}" + acceptConnectionRequestEvent.getSenderId();
        log.info(message);
        sendNotificationService.send(acceptConnectionRequestEvent.getSenderId(), message);
    }
}

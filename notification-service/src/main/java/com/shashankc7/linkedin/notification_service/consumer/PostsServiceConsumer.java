package com.shashankc7.linkedin.notification_service.consumer;

import com.shashankc7.linkedin.notification_service.clients.ConnectionsClient;
import com.shashankc7.linkedin.notification_service.dto.PersonDto;
import com.shashankc7.linkedin.notification_service.service.SendNotificationService;
import com.shashankc7.linkedin.posts_service.event.PostCreatedEvent;
import com.shashankc7.linkedin.posts_service.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer
{
    private final SendNotificationService sendNotification;
    private final ConnectionsClient connectionsClient;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent)
    {
        log.info("Sending notifications : handlePostCreated");
        List<PersonDto> connections = connectionsClient.getFirstDegreeConnections(postCreatedEvent.getCreatorId());

        for(PersonDto connection: connections)
        {
            sendNotification.send(connection.getUserId(), "your conection "+ postCreatedEvent.getCreatorId()+" has created a post !");
        }

    }

    @KafkaListener(topics= "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent)
    {
        log.info("Sending notifications : handlePostCreated");
        String message = String.format("Your post, %d has been liked by %d", postLikedEvent.getPostId(),
                postLikedEvent.getLikedByUserId());

        sendNotification.send(postLikedEvent.getCreatorId(), message);
    }

}

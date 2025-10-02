package com.shashankc7.linkedin.connection_service.service;

import com.shashankc7.linkedin.connection_service.auth.UserContextHolder;
import com.shashankc7.linkedin.connection_service.entity.Person;
import com.shashankc7.linkedin.connection_service.event.AcceptConnectionRequestEvent;
import com.shashankc7.linkedin.connection_service.event.SendConnectionRequestEvent;
import com.shashankc7.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService
{
    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendConnectionRequestEventKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptConnectionRequestEventKafkaTemplate;

    public List<Person> getFirstDegreeConnections()
    {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first degree connections for use with id: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }

    public Boolean sendConnectionRequest(Long receiverId)
    {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request, sender: {}, receiver: {}", senderId, receiverId);

        if(Objects.equals(senderId, receiverId)) throw new RuntimeException("Cannot send request to yourself !!");

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if(alreadySentRequest)
        {
            throw new RuntimeException("Connection request already exists, cannot send again !");
        }
        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected)
        {
            throw new RuntimeException("You are already connected with the user !");
        }

        log.info("Successfully sent connect request to {}", receiverId);

        personRepository.addConnectionRequest(senderId, receiverId);

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder().senderId(senderId).receiverId(receiverId).build();
        sendConnectionRequestEventKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);
        return true;
    }

    public Boolean acceptConnectionRequest(Long senderId)
    {
        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if(!connectionRequestExists)
        {
            throw new RuntimeException("No connection requests exists to accept");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted the connection request, sender: {}, receiver: {}", senderId, receiverId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder().senderId(senderId).receiverId(receiverId).build();
        acceptConnectionRequestEventKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);

        return true;
    }

    public Boolean rejectConnectionRequest(Long senderId)
    {
        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if(!connectionRequestExists)
        {
            throw new RuntimeException("No connection requests exists to reject");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }

    public Boolean createPerson(Long userId, String name)
    {
        log.info("Creating Person with userId : {}", userId);

        Person person = new Person();
        person.setUserId(userId);
        person.setName(name);

        if(personRepository.existsByUserId(userId))
        {
            log.warn("User already registered in neo4j db");
            throw new RuntimeException("Person already registered !!");
        }

        personRepository.save(person);
        return true;
    }
}

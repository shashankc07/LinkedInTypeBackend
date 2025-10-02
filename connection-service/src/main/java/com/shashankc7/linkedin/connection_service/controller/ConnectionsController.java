package com.shashankc7.linkedin.connection_service.controller;

import com.shashankc7.linkedin.connection_service.dto.UserDto;
import com.shashankc7.linkedin.connection_service.entity.Person;
import com.shashankc7.linkedin.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController
{
    private final ConnectionService connectionService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections()
    {
        return ResponseEntity.ok(connectionService.getFirstDegreeConnections());
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.sendConnectionRequest(userId));

    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(userId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createPerson(UserDto userDto)
    {
        Long userId = userDto.getId();
        String name = userDto.getName();

        return ResponseEntity.ok(connectionService.createPerson(userId, name));
    }
}

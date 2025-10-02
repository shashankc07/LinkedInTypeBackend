package com.shashankc7.linkedin.user_service.clients;

import com.shashankc7.linkedin.user_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsClient
{
    @PostMapping("/core/create")
    public ResponseEntity<Boolean> createPerson(UserDto userDto);

}
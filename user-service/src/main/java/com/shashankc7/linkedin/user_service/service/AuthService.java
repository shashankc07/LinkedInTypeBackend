package com.shashankc7.linkedin.user_service.service;

import com.shashankc7.linkedin.user_service.clients.ConnectionsClient;
import com.shashankc7.linkedin.user_service.dto.LoginRequestDto;
import com.shashankc7.linkedin.user_service.dto.SignupRequestDto;
import com.shashankc7.linkedin.user_service.dto.UserDto;
import com.shashankc7.linkedin.user_service.entitiy.User;
import com.shashankc7.linkedin.user_service.exception.BadRequestException;
import com.shashankc7.linkedin.user_service.exception.ResourceNotFoundException;
import com.shashankc7.linkedin.user_service.repository.UserRepository;
import com.shashankc7.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final ConnectionsClient connectionsClient;

    public UserDto signUp(SignupRequestDto signupRequestDto)
    {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());

        if(exists)
        {
            throw new BadRequestException("User already exists ! "+ signupRequestDto.getEmail());
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);

        connectionsClient.createPerson(modelMapper.map(savedUser, UserDto.class));

        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto)
    {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+ loginRequestDto.getEmail()));

        boolean ispasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

        if(!ispasswordMatch)
        {
            throw new BadRequestException("Incorrect password !");
        }

        return jwtService.generateAccessToken(user);
    }
}

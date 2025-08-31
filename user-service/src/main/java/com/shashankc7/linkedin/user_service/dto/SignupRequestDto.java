package com.shashankc7.linkedin.user_service.dto;

import lombok.Data;

@Data
public class SignupRequestDto
{
    private String name;
    private String email;
    private String password;
}

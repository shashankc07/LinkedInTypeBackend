package com.shashankc7.linkedin.user_service.dto;

import lombok.Data;

@Data
public class LoginRequestDto
{
    private String email;
    private String password;
}
